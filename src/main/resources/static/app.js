const API_BASE = '';
const state = {
    token: localStorage.getItem('smartAgriToken'),
    username: localStorage.getItem('smartAgriUsername'),
    role: localStorage.getItem('smartAgriRole'),
    groups: [],
    farmers: [],
    commodities: [],
    stocks: [],
    distributions: [],
    monitoring: [],
    users: []
};

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => document.querySelectorAll(selector);
const esc = (value) => String(value ?? '').replace(/[&<>'"]/g, char => ({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','"':'&quot;'}[char]));
const money = (value) => Number(value ?? 0).toLocaleString('id-ID', { maximumFractionDigits: 2 });
const today = () => new Date().toISOString().slice(0, 10);

function notify(message, isError = false) {
    const toast = $('#toast');
    toast.textContent = message;
    toast.classList.toggle('error', isError);
    toast.classList.remove('hidden');
    setTimeout(() => toast.classList.add('hidden'), 3400);
}

async function api(path, options = {}) {
    const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
    if (state.token) headers.Authorization = `Bearer ${state.token}`;

    const response = await fetch(`${API_BASE}${path}`, { ...options, headers });
    const text = await response.text();
    let json = null;
    try {
        json = text ? JSON.parse(text) : null;
    } catch (error) {
        if (!response.ok) throw new Error(`Request gagal (${response.status})`);
        throw new Error('Response server bukan JSON yang valid.');
    }

    if (!response.ok || (json && json.success === false)) {
        const message = json?.message || `Request gagal (${response.status})`;
        throw new Error(message);
    }
    return json?.data ?? json;
}

function bodyFromForm(form) {
    const data = new FormData(form);
    const payload = {};
    for (const [key, value] of data.entries()) {
        if (key === 'commodityIds') continue;
        payload[key] = value === '' ? null : value;
    }
    if (form.elements.commodityIds) {
        payload.commodityIds = Array.from(form.elements.commodityIds.selectedOptions).map(opt => Number(opt.value));
    }
    ['farmerId', 'farmerGroupId', 'commodityId', 'stockId'].forEach(key => {
        if (payload[key] !== undefined && payload[key] !== null) payload[key] = Number(payload[key]);
    });
    ['quantity'].forEach(key => {
        if (payload[key] !== undefined && payload[key] !== null) payload[key] = Number(payload[key]);
    });
    return payload;
}

function showLogin() {
    $('#login-page').classList.remove('hidden');
    $('#app-page').classList.add('hidden');
}

function showApp() {
    $('#login-page').classList.add('hidden');
    $('#app-page').classList.remove('hidden');
    $('#user-chip').textContent = `${state.username || 'user'} · ${state.role || ''}`;
    applyRoleUi();
}

function applyRoleUi() {
    const userNav = document.querySelector('[data-section="users"]');
    if (userNav) userNav.style.display = state.role === 'ROLE_ADMIN' ? '' : 'none';
}

async function login(event) {
    event.preventDefault();
    const username = $('#login-username').value.trim();
    const password = $('#login-password').value;
    $('#login-message').textContent = 'Memproses login...';
    try {
        const result = await api('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password })
        });
        state.token = result.token;
        state.username = result.username;
        state.role = result.role;
        localStorage.setItem('smartAgriToken', state.token);
        localStorage.setItem('smartAgriUsername', state.username);
        localStorage.setItem('smartAgriRole', state.role);
        $('#login-message').textContent = '';
        showApp();
        await loadAll();
        notify('Login berhasil. Selamat datang di NusaPanen!');
    } catch (error) {
        $('#login-message').textContent = error.message;
        notify(error.message, true);
    }
}

function logout() {
    localStorage.removeItem('smartAgriToken');
    localStorage.removeItem('smartAgriUsername');
    localStorage.removeItem('smartAgriRole');
    state.token = null;
    state.username = null;
    state.role = null;
    showLogin();
}

async function loadAll() {
    try {
        const usersRequest = state.role === 'ROLE_ADMIN' ? api('/api/users') : Promise.resolve([]);
        const [groups, farmers, commodities, stocks, distributions, monitoring, users] = await Promise.all([
            api('/api/groups'),
            api('/api/farmers'),
            api('/api/commodities'),
            api('/api/stocks'),
            api('/api/distributions'),
            api('/api/monitoring/availability'),
            usersRequest
        ]);
        Object.assign(state, { groups, farmers, commodities, stocks, distributions, monitoring, users });
        renderAll();
    } catch (error) {
        if (String(error.message).includes('403') || String(error.message).includes('401')) {
            notify('Sesi tidak valid atau role tidak punya akses. Silakan login ulang.', true);
        } else {
            notify(error.message, true);
        }
    }
}

function renderAll() {
    renderDashboard();
    renderSelects();
    renderGroups();
    renderFarmers();
    renderCommodities();
    renderStocks();
    renderDistributions();
    renderUsers();
}

function renderDashboard() {
    $('#stat-groups').textContent = state.groups.length;
    $('#stat-farmers').textContent = state.farmers.length;
    $('#stat-commodities').textContent = state.commodities.length;
    const totalAvailable = state.monitoring.reduce((sum, item) => sum + Number(item.availableStock || 0), 0);
    $('#stat-available').textContent = money(totalAvailable);

    const container = $('#monitoring-cards');
    if (!state.monitoring.length) {
        container.innerHTML = '<div class="monitor-card"><strong>Belum ada data</strong><p class="muted">Tambahkan komoditas dan stok pangan terlebih dahulu.</p></div>';
        return;
    }
    container.innerHTML = state.monitoring.map(item => `
        <div class="monitor-card">
            <strong>${esc(item.commodityName)}</strong>
            <div class="monitor-row"><span>Total stok</span><b>${money(item.totalStock)} ${esc(item.unit)}</b></div>
            <div class="monitor-row"><span>Distribusi</span><b>${money(item.totalDistributed)} ${esc(item.unit)}</b></div>
            <div class="monitor-row"><span>Tersedia</span><b>${money(item.availableStock)} ${esc(item.unit)}</b></div>
            <span class="status ${esc(item.status)}">${esc(item.status)}</span>
        </div>
    `).join('');
}

function renderSelects() {
    const groupOptions = ['<option value="">Pilih kelompok tani</option>', ...state.groups.map(g => `<option value="${g.id}">${esc(g.name)} · ${esc(g.village)}</option>`)].join('');
    const farmerOptions = ['<option value="">Pilih petani</option>', ...state.farmers.map(f => `<option value="${f.id}">${esc(f.fullName)}</option>`)].join('');
    const commodityOptions = ['<option value="">Pilih komoditas</option>', ...state.commodities.map(c => `<option value="${c.id}">${esc(c.name)} (${esc(c.unit)})</option>`)].join('');
    const stockOptions = ['<option value="">Pilih stok</option>', ...state.stocks.map(s => `<option value="${s.id}">${esc(s.commodity?.name)} · ${esc(s.farmer?.fullName)} · ${money(s.quantity)} ${esc(s.commodity?.unit)}</option>`)].join('');

    $('#farmer-form select[name="farmerGroupId"]').innerHTML = groupOptions;
    $('#stock-form select[name="farmerId"]').innerHTML = farmerOptions;
    $('#stock-form select[name="commodityId"]').innerHTML = commodityOptions;
    $('#distribution-form select[name="stockId"]').innerHTML = stockOptions;
    $('#user-form select[name="farmerId"]').innerHTML = '<option value="">Tidak dihubungkan</option>' + state.farmers.map(f => `<option value="${f.id}">${esc(f.fullName)}</option>`).join('');
    $('#group-form select[name="commodityIds"]').innerHTML = state.commodities.map(c => `<option value="${c.id}">${esc(c.name)}</option>`).join('');
}

function emptyRow(colspan, text) {
    return `<tr><td colspan="${colspan}" class="empty-row">${esc(text)}</td></tr>`;
}

function actions(type, id) {
    return `<div class="action-bar"><button class="secondary-btn" onclick="edit${type}(${id})">Edit</button><button class="danger-btn" onclick="removeItem('${type}', ${id})">Hapus</button></div>`;
}

function renderGroups() {
    $('#groups-table').innerHTML = state.groups.length ? state.groups.map(g => `
        <tr><td>${g.id}</td><td><b>${esc(g.name)}</b></td><td>${esc(g.village)}<br><small>${esc(g.district || '-')}</small></td><td>${(g.commodities || []).map(c => esc(c.name)).join(', ') || '-'}</td><td>${actions('Group', g.id)}</td></tr>
    `).join('') : emptyRow(5, 'Belum ada kelompok tani.');
}

function renderFarmers() {
    $('#farmers-table').innerHTML = state.farmers.length ? state.farmers.map(f => `
        <tr><td>${f.id}</td><td><b>${esc(f.fullName)}</b><br><small>${esc(f.address)}</small></td><td>${esc(f.phone)}</td><td>${esc(f.farmerGroup?.name || '-')}</td><td>${actions('Farmer', f.id)}</td></tr>
    `).join('') : emptyRow(5, 'Belum ada petani.');
}

function renderCommodities() {
    $('#commodities-table').innerHTML = state.commodities.length ? state.commodities.map(c => `
        <tr><td>${c.id}</td><td><b>${esc(c.name)}</b></td><td>${esc(c.category)}</td><td>${esc(c.unit)}</td><td>${actions('Commodity', c.id)}</td></tr>
    `).join('') : emptyRow(5, 'Belum ada komoditas.');
}

function renderStocks() {
    $('#stocks-table').innerHTML = state.stocks.length ? state.stocks.map(s => `
        <tr><td>${s.id}</td><td><b>${esc(s.commodity?.name)}</b><br><small>${esc(s.location || '-')}</small></td><td>${esc(s.farmer?.fullName)}</td><td>${money(s.quantity)} ${esc(s.commodity?.unit)}</td><td>${esc(s.harvestDate)}</td><td>${actions('Stock', s.id)}</td></tr>
    `).join('') : emptyRow(6, 'Belum ada stok pangan.');
}

function renderDistributions() {
    $('#distributions-table').innerHTML = state.distributions.length ? state.distributions.map(d => `
        <tr><td>${d.id}</td><td><b>${esc(d.stock?.commodity?.name)}</b><br><small>Stok #${esc(d.stock?.id)}</small></td><td>${money(d.quantity)} ${esc(d.stock?.commodity?.unit)}</td><td>${esc(d.destination)}<br><small>${esc(d.receiverName || '-')}</small></td><td>${esc(d.distributionDate)}</td><td>${actions('Distribution', d.id)}</td></tr>
    `).join('') : emptyRow(6, 'Belum ada distribusi.');
}

function renderUsers() {
    const table = $('#users-table');
    if (!table) return;
    table.innerHTML = state.users.length ? state.users.map(u => `
        <tr>
            <td>${u.id}</td>
            <td><b>${esc(u.username)}</b></td>
            <td><span class="role-badge">${esc(u.role)}</span></td>
            <td>${esc(u.farmer?.fullName || '-')}</td>
            <td>${actions('User', u.id)}</td>
        </tr>
    `).join('') : emptyRow(5, 'Belum ada data user.');
}

async function saveForm(event, type, endpoint) {
    event.preventDefault();
    const form = event.target;
    const payload = bodyFromForm(form);
    const editingId = form.dataset.editing;
    const method = editingId ? 'PUT' : 'POST';
    const path = editingId ? `${endpoint}/${editingId}` : endpoint;
    try {
        await api(path, { method, body: JSON.stringify(payload) });
        notify(editingId ? 'Data berhasil diperbarui.' : 'Data berhasil ditambahkan.');
        resetForm(type);
        await loadAll();
    } catch (error) {
        notify(error.message, true);
    }
}

function resetForm(type) {
    const form = $(`#${type}-form`);
    if (!form) return;
    form.reset();
    delete form.dataset.editing;
    const title = $(`#${type}-form-title`);
    const titles = {
        group: 'Tambah Kelompok Tani', farmer: 'Tambah Petani', commodity: 'Tambah Komoditas', stock: 'Catat Stok Pangan', distribution: 'Catat Distribusi', user: 'Tambah User'
    };
    if (title) title.textContent = titles[type] || title.textContent;
    if (type === 'user' && form.elements.password) form.elements.password.required = true;
    setDefaultDates();
}

function setSelectedValues(select, values) {
    const set = new Set(values.map(String));
    Array.from(select.options).forEach(option => option.selected = set.has(option.value));
}

window.editGroup = (id) => {
    const item = state.groups.find(x => x.id === id);
    const form = $('#group-form');
    form.dataset.editing = id;
    form.elements.name.value = item.name || '';
    form.elements.village.value = item.village || '';
    form.elements.district.value = item.district || '';
    form.elements.description.value = item.description || '';
    setSelectedValues(form.elements.commodityIds, (item.commodities || []).map(c => c.id));
    $('#group-form-title').textContent = `Edit Kelompok #${id}`;
    showSection('groups');
};

window.editFarmer = (id) => {
    const item = state.farmers.find(x => x.id === id);
    const form = $('#farmer-form');
    form.dataset.editing = id;
    form.elements.fullName.value = item.fullName || '';
    form.elements.phone.value = item.phone || '';
    form.elements.address.value = item.address || '';
    form.elements.farmerGroupId.value = item.farmerGroup?.id || '';
    $('#farmer-form-title').textContent = `Edit Petani #${id}`;
    showSection('farmers');
};

window.editCommodity = (id) => {
    const item = state.commodities.find(x => x.id === id);
    const form = $('#commodity-form');
    form.dataset.editing = id;
    form.elements.name.value = item.name || '';
    form.elements.category.value = item.category || 'PADI';
    form.elements.unit.value = item.unit || '';
    $('#commodity-form-title').textContent = `Edit Komoditas #${id}`;
    showSection('commodities');
};

window.editStock = (id) => {
    const item = state.stocks.find(x => x.id === id);
    const form = $('#stock-form');
    form.dataset.editing = id;
    form.elements.farmerId.value = item.farmer?.id || '';
    form.elements.commodityId.value = item.commodity?.id || '';
    form.elements.quantity.value = item.quantity || '';
    form.elements.harvestDate.value = item.harvestDate || today();
    form.elements.location.value = item.location || '';
    $('#stock-form-title').textContent = `Edit Stok #${id}`;
    showSection('stocks');
};

window.editDistribution = (id) => {
    const item = state.distributions.find(x => x.id === id);
    const form = $('#distribution-form');
    form.dataset.editing = id;
    form.elements.stockId.value = item.stock?.id || '';
    form.elements.quantity.value = item.quantity || '';
    form.elements.destination.value = item.destination || '';
    form.elements.receiverName.value = item.receiverName || '';
    form.elements.distributionDate.value = item.distributionDate || today();
    $('#distribution-form-title').textContent = `Edit Distribusi #${id}`;
    showSection('distributions');
};

window.removeItem = async (type, id) => {
    const endpoints = { Group: '/api/groups', Farmer: '/api/farmers', Commodity: '/api/commodities', Stock: '/api/stocks', Distribution: '/api/distributions', User: '/api/users' };
    if (!confirm(`Hapus data #${id}?`)) return;
    try {
        await api(`${endpoints[type]}/${id}`, { method: 'DELETE' });
        notify('Data berhasil dihapus.');
        await loadAll();
    } catch (error) {
        notify(error.message, true);
    }
};

window.editUser = (id) => {
    const item = state.users.find(x => x.id === id);
    const form = $('#user-form');
    form.dataset.editing = id;
    form.elements.username.value = item.username || '';
    form.elements.password.value = '';
    form.elements.password.required = false;
    form.elements.role.value = item.role || 'ROLE_PETUGAS';
    form.elements.farmerId.value = item.farmer?.id || '';
    $('#user-form-title').textContent = `Edit User #${id}`;
    showSection('users');
};

async function saveUser(event) {
    event.preventDefault();
    const form = event.target;
    const payload = bodyFromForm(form);
    const editingId = form.dataset.editing;

    if (!editingId && !payload.password) {
        notify('Password wajib diisi saat tambah user.', true);
        return;
    }

    if (editingId && !payload.password) {
        delete payload.password;
    }

    if (!payload.farmerId) payload.farmerId = null;

    try {
        await api(editingId ? `/api/users/${editingId}` : '/api/users', {
            method: editingId ? 'PUT' : 'POST',
            body: JSON.stringify(payload)
        });
        notify(editingId ? 'User berhasil diperbarui.' : 'User baru berhasil dibuat.');
        resetForm('user');
        form.elements.password.required = true;
        await loadAll();
    } catch (error) {
        notify(error.message, true);
    }
}

function showSection(sectionId) {
    $$('.page-section').forEach(section => section.classList.toggle('active-section', section.id === sectionId));
    $$('.nav-link').forEach(link => link.classList.toggle('active', link.dataset.section === sectionId));
    const activeText = document.querySelector(`.nav-link[data-section="${sectionId}"]`)?.textContent.trim().replace(/^[^\s]+\s/, '') || 'Dashboard';
    $('#page-title').textContent = activeText;
}

function setDefaultDates() {
    ['#stock-form input[name="harvestDate"]', '#distribution-form input[name="distributionDate"]'].forEach(selector => {
        const field = $(selector);
        if (field && !field.value) field.value = today();
    });
}

function bindEvents() {
    $('#login-form').addEventListener('submit', login);
    $('#logout-btn').addEventListener('click', logout);
    $('#refresh-btn').addEventListener('click', loadAll);
    $$('.nav-link').forEach(link => link.addEventListener('click', () => showSection(link.dataset.section)));
    $$('[data-reset]').forEach(button => button.addEventListener('click', () => resetForm(button.dataset.reset)));

    $('#group-form').addEventListener('submit', e => saveForm(e, 'group', '/api/groups'));
    $('#farmer-form').addEventListener('submit', e => saveForm(e, 'farmer', '/api/farmers'));
    $('#commodity-form').addEventListener('submit', e => saveForm(e, 'commodity', '/api/commodities'));
    $('#stock-form').addEventListener('submit', e => saveForm(e, 'stock', '/api/stocks'));
    $('#distribution-form').addEventListener('submit', e => saveForm(e, 'distribution', '/api/distributions'));
    $('#user-form').addEventListener('submit', saveUser);
    $('#user-form input[name="password"]').required = true;
}

async function init() {
    bindEvents();
    setDefaultDates();
    if (state.token) {
        showApp();
        await loadAll();
    } else {
        showLogin();
    }
}

init();
