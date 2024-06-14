package org.example.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public List<AdminApproveVo> getAllApprovals() {
        return adminDao.getAllApprovals();
    }
}
