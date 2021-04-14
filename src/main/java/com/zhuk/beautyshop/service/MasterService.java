package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.repo.MasterRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class MasterService {

    @Value("${upload.path}")
    private String uploadPath;

    private MasterRepo masterRepo;

    public MasterService(MasterRepo masterRepo) {
        this.masterRepo = masterRepo;
    }

    public Page<Master> findAll(User userInfo, Pageable pageable) {
        if (userInfo == null) {
            return masterRepo.findAll(pageable);
        }
        return masterRepo.findAllByUserInfoIsNot(userInfo, pageable);
    }

    public Page<Master> findAllBySpecialitiesContaining(User userInfo, String category, Pageable pageable) {
        ServiceCategory serviceCategory = ServiceCategory.valueOf(category);
        if (userInfo == null) {
            return masterRepo.findAllBySpecialitiesContaining(serviceCategory, pageable);
        }
        return masterRepo.findAllByUserInfoIsNotAndSpecialitiesContaining(userInfo, serviceCategory, pageable);
    }

    public List<Master> findAll() {
        return masterRepo.findAll();
    }

    public Page<Master> findAllByIdNotIn(User userInfo, Collection<Long> id, Pageable pageable) {
        if (userInfo == null) {
            if (id.isEmpty()) {
                return masterRepo.findAll(pageable);
            }
            return masterRepo.findAllByIdNotIn(id, pageable);
        } else {
            if (id.isEmpty()) {
                return masterRepo.findAllByUserInfoIsNot(userInfo, pageable);
            }
            return masterRepo.findAllByUserInfoIsNotAndIdNotIn(userInfo, id, pageable);
        }
    }

    public Page<Master> findAllByIdNotInAndSpecialitiesContaining(Collection<Long> id, User userInfo, String category, Pageable pageable) {
        ServiceCategory serviceCategory = ServiceCategory.valueOf(category);
        if (userInfo == null) {
            if (id.isEmpty()) {
                return masterRepo.findAllBySpecialitiesContaining(serviceCategory, pageable);
            }
            return masterRepo.findAllByIdNotInAndSpecialitiesContaining(id, serviceCategory, pageable);
        } else {
            if (id.isEmpty()) {
                return masterRepo.findAllByUserInfoIsNotAndSpecialitiesContaining(userInfo, serviceCategory, pageable);
            }
            return masterRepo.findAllByUserInfoIsNotAndIdNotInAndSpecialitiesContaining(userInfo, id, serviceCategory, pageable);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Master findFirstById(Long id) {
        return masterRepo.findFirstById(id);
    }

    public Master getFirstByUserInfoId(Long id) {
        return masterRepo.getFirstByUserInfoId(id);
    }

    public void save(Master master) {
        masterRepo.save(master);
    }

    public Master findByEmail(String email) {
        return masterRepo.findFirstByUserInfoEmail(email);
    }

    public void updateMasterImage(MultipartFile file, Master master) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + fileName));

            master.setImage(fileName);
        }
    }
}
