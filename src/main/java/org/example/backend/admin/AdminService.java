package org.example.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public List<AdminApproveVo> postAllApprovals() {
        return adminDao.postAllApprovals();
    }

    public void setAdminApproval(int owner_id) {
        System.out.println("[AdminMemberService] setAdminApproval()");

        int result = adminDao.adminApprovalupdate(owner_id);

    }
}
