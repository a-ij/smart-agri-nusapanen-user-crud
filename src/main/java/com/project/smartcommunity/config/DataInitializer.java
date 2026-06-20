package com.project.smartcommunity.config;

import com.project.smartcommunity.model.AppUser;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.model.CommodityCategory;
import com.project.smartcommunity.model.Distribution;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.model.FarmerGroup;
import com.project.smartcommunity.model.Role;
import com.project.smartcommunity.model.Stock;
import com.project.smartcommunity.repository.AppUserRepository;
import com.project.smartcommunity.repository.CommodityRepository;
import com.project.smartcommunity.repository.DistributionRepository;
import com.project.smartcommunity.repository.FarmerGroupRepository;
import com.project.smartcommunity.repository.FarmerRepository;
import com.project.smartcommunity.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDefaultData(AppUserRepository appUserRepository,
                                      FarmerGroupRepository farmerGroupRepository,
                                      FarmerRepository farmerRepository,
                                      CommodityRepository commodityRepository,
                                      StockRepository stockRepository,
                                      DistributionRepository distributionRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (!appUserRepository.existsByUsername("admin")) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ROLE_ADMIN);
                appUserRepository.save(admin);
                System.out.println("Default admin dibuat: username=admin, password=admin123");
            }

            if (commodityRepository.count() == 0 && farmerGroupRepository.count() == 0) {
                Commodity beras = new Commodity();
                beras.setName("Beras Premium");
                beras.setCategory(CommodityCategory.PADI);
                beras.setUnit("kg");
                commodityRepository.save(beras);

                Commodity jagung = new Commodity();
                jagung.setName("Jagung Manis");
                jagung.setCategory(CommodityCategory.JAGUNG);
                jagung.setUnit("kg");
                commodityRepository.save(jagung);

                Commodity cabai = new Commodity();
                cabai.setName("Cabai Merah");
                cabai.setCategory(CommodityCategory.SAYUR);
                cabai.setUnit("kg");
                commodityRepository.save(cabai);

                FarmerGroup group = new FarmerGroup();
                group.setName("Kelompok Tani Makmur");
                group.setVillage("Cibiru");
                group.setDistrict("Bandung");
                group.setDescription("Kelompok tani contoh untuk demo sistem informasi pertanian.");
                group.getCommodities().add(beras);
                group.getCommodities().add(jagung);
                farmerGroupRepository.save(group);

                Farmer farmer = new Farmer();
                farmer.setFullName("Dedi Supriadi");
                farmer.setPhone("081234567890");
                farmer.setAddress("Kp. Sawah Hijau No. 12");
                farmer.setFarmerGroup(group);
                farmerRepository.save(farmer);

                Stock stockBeras = new Stock();
                stockBeras.setFarmer(farmer);
                stockBeras.setCommodity(beras);
                stockBeras.setQuantity(new BigDecimal("250"));
                stockBeras.setHarvestDate(LocalDate.now().minusDays(4));
                stockBeras.setLocation("Gudang Desa Cibiru");
                stockRepository.save(stockBeras);

                Stock stockCabai = new Stock();
                stockCabai.setFarmer(farmer);
                stockCabai.setCommodity(cabai);
                stockCabai.setQuantity(new BigDecimal("45"));
                stockCabai.setHarvestDate(LocalDate.now().minusDays(2));
                stockCabai.setLocation("Gudang Komoditas Sayur");
                stockRepository.save(stockCabai);

                Distribution distribution = new Distribution();
                distribution.setStock(stockBeras);
                distribution.setQuantity(new BigDecimal("60"));
                distribution.setDestination("Pasar Desa");
                distribution.setReceiverName("Bu Rina");
                distribution.setDistributionDate(LocalDate.now().minusDays(1));
                distributionRepository.save(distribution);

                System.out.println("Data demo pertanian berhasil dibuat.");
            }
        };
    }
}
