package org.example.backend.admin;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.store.StoreOrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 모든 상점 승인 정보 가져오기
    @PostMapping("/approvals")
    public List<AdminApproveVo> postAllApprovals() {
        return adminService.postAllApprovals();
    }

    // GET 요청을 통해 승인 처리하기
    // GET 요청: axios를 사용하여 GET 요청을 보내고, URL에 쿼리 파라미터를 포함시킬 수 있습니다.
    // const response = await axios.get(`http://localhost:8080/admin/approve?owner_id=${id}`);
    // 이 경우, owner_id가 URL의 쿼리 스트링으로 전달되어 Spring Boot의 @RequestParam으로 받을 수 있습니다.

    @GetMapping("/approve")
    public String setAdminApproval(@RequestParam("owner_id") int owner_id) {
        System.out.println("[AdminController] setAdminApproval() owner_id: " + owner_id);

        String nextPage = "redirect:http://localhost:3000/ManagerApprove";
        adminService.setAdminApproval(owner_id);
        return nextPage;

    }

    //관리자 주문 내역 불러오기
    @GetMapping("/OrderReceipt")
    public List<AdminOrderInformationVo> orderReceipt(){
        log.info("결제 내역 조회!");
        return adminService.orderReceipt();
    }

    //관리자 매출 내역 그래프로 불러오기
    @GetMapping("/ManagerRevenue")
    public List<AdminOrderInformationVo> ManagerRevenue(@RequestParam("order_approval_status") int order_approval_status){
        log.info("현재 매출 내역 조회하기! " + ", order_approval_status (주문 승인 상태 값 조회) : " + order_approval_status);
        return adminService.ManagerRevenue(order_approval_status);
    }
}
//    실패한코드 이유? (당연하지 RequestMethod.POST 로 요청했으므로 Body로 받아와야 한다.)
//    @RequestMapping(value = "/approve", method = RequestMethod.POST)
//    public void setAdminApproval(@RequestParam("owner_id") int owner_id){
//        System.out.println("[AdminController] setAdminApproval()");
//
//        adminService.setAdminApproval(owner_id);
//    }
