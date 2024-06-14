package org.example.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 상점 승인 정보 가져오기
    @GetMapping("/approvals")
    public List<AdminApproveVo> getAllApprovals() {
        return adminService.getAllApprovals();
    }
}
