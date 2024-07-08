package org.example.backend.admin;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.admin.dto.*;
import org.example.backend.service.StoreReportVo;
import org.example.backend.store.dto.ReportsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    //업체쪽에서 유저 신고한 내용확인하기
    @GetMapping("/Reports")
    public List<ReportsUserVo> userReport(){
        return adminService.userReport();
    }

    //유저 신고 내용 상세 조회
    @GetMapping("ReportsDetail")
    public List<ReportsUserDetailVo> userDetail(@RequestParam("id") int authorId){
        return adminService.userDetail(authorId);
    }

    //유저 블락 먹이기
    @PostMapping("block")
    public ResponseEntity<?> block (@RequestBody Map<String, Integer> id){
        //아이디값을 받아와서 그 아이디 값하고 같은 이메일의 유저 권한을 변경한다.
        int rs=adminService.block(id.get("id"));
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
//
    }

    //유저쪽에서 업체 신고한 내용확인하기
    @GetMapping("/StoreReports")
    public List<ReportStoreVo> storeReport(){
        return adminService.storeReport();
    }

    //유저쪽에서 업체 신고한 내용 상세조회
    @GetMapping("StoreReportsDetail")
    public List<ReportStoreDetailVo> storeDetail(@RequestParam("storeId") int storeId){
        return adminService.storeDetail(storeId);
    }

    //업체 정지시키기 가시성 2 로 지정하기
    @PostMapping("Storeblock")
    public ResponseEntity<?> Storeblockblock (@RequestBody Map<String, Integer> id){
        //아이디값을 받아와서 그 아이디 값하고 같은 이메일의 유저 권한을 변경한다.
        int rs=adminService.Storeblockblock(id.get("id"));
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
//
    }



}

