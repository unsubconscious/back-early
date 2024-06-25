package org.example.backend.store;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
//    private static final String URL="C:\\Users\\KOSTA\\Desktop\\finalfr\\public\\imgs\\";
//    private static final String URL="C:\\Users\\kjk98\\OneDrive\\바탕 화면\\koster\\frontend\\public\\imgs\\";

    //내꺼
//    private static final String URL="E:\\h\\DeliveryOracle\\frontend\\public\\imgs";
    //소니
    private static final String URL="C:\\GitSource\\front_com\\public\\imgs\\";
    //상점등록
    @PostMapping("/join")
    public String storeJoin(        @RequestParam("name") String name,
                                    @RequestParam("address") String address,
                                    @RequestParam("text") String text,
                                    @RequestParam("img") MultipartFile img,
                                    @RequestParam("storeX") BigDecimal storeX,
                                    @RequestParam("storeY") BigDecimal storeY,
                                    @RequestParam("category") String category,
                                    @RequestParam("id") int id
    ) throws IOException {

        log.info(":::: 업체등록 ::::");

        //이미지 저장하기
        String saveName=null;
        if (img != null && !img.isEmpty()) {
            File uploadDir= new File(URL);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
                System.out.println("디렉토리 파일 생성");
            }

            //원본파일이름
            String originalName= img.getOriginalFilename();
            //파일 이름 UUID를 사용 하여 재정의
            UUID uuid = UUID.randomUUID();
            saveName=uuid.toString()+"_"+originalName;

            //파일생성
            File saveFile=new File(URL+saveName);
            img.transferTo(saveFile);
        }
        StoreRegistrationVo storeRegistrationVo = new StoreRegistrationVo();
        storeRegistrationVo.setStore_name(name);
        storeRegistrationVo.setStore_address(address);
        storeRegistrationVo.setStore_description(text);
        storeRegistrationVo.setStore_image(saveName);
        storeRegistrationVo.setStore_x(storeX);
        storeRegistrationVo.setStore_y(storeY);
        storeRegistrationVo.setStore_ca(category);
        //유저아이디 나중에는 받아와야한다.
        storeRegistrationVo.setOwner_id(id);

        System.out.println(address);

        storeService.storeInsert(storeRegistrationVo);

        return "등록";
    }

    //등록 승인 되었는지 확인 요청
    @GetMapping("/menuRs")
    public int approvR(@RequestParam("id") int id){
        log.info(":::: 업체 승인 확인 요청 ::::");

        int rs=storeService.count(id);
        if(rs>0){
            return rs;
        }
        else {
            return -1;
        }
    }

    //메뉴 등록하기
    @PostMapping("menuRs")
    public int menuRs(@RequestParam("name") String name,
                      @RequestParam("price") int price,
                      @RequestParam("img") MultipartFile img,
                      @RequestParam("shopid") int storeIds) throws IOException {
        log.info(":::: 메뉴등록 ::::");

        //이미지 저장하기
        String saveName=null;
        if (img != null && !img.isEmpty()) {
            File uploadDir= new File(URL);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
                System.out.println("디렉토리 파일 생성");
            }

            //원본파일이름
            String originalName= img.getOriginalFilename();
            //파일 이름 UUID를 사용 하여 재정의
            UUID uuid = UUID.randomUUID();
            saveName=uuid.toString()+"_"+originalName;

            //파일생성
            File saveFile=new File(URL+saveName);
            img.transferTo(saveFile);
        }

        StoreInformationVo StoreInformationVo = new StoreInformationVo();
        StoreInformationVo.setStoreId(storeIds);
        StoreInformationVo.setMenuName(name);
        StoreInformationVo.setMenuPrice(price);
        StoreInformationVo.setMenuImage(saveName);
        log.info(":::: 메뉴등록 성공 ::::");

        return storeService.menuRs(StoreInformationVo);

    }

    //메뉴 목록 불러오기
    @GetMapping("/menulist")
    public List<StoreInformationVo> menuList(@RequestParam("shopid") int id){
        log.info(":::: 메뉴불러오기 ::::");
        return storeService.menuList(id);

    }
    //메뉴 수정하기

    @PostMapping("menuedit")

    public int menuedit(@RequestParam("name") String name,
                      @RequestParam("price") int price,
                        @RequestParam(value = "img", required = false) MultipartFile img,
                      @RequestParam("shopid") int storeIds) throws IOException {


        log.info(":::: 메뉴수정하기 ::::");
        //이미지 저장하기
        String saveName=null;
        if (img != null && !img.isEmpty()) {
            File uploadDir= new File(URL);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
                System.out.println("디렉토리 파일 생성");
            }

            //원본파일이름
            String originalName= img.getOriginalFilename();
            //파일 이름 UUID를 사용 하여 재정의
            UUID uuid = UUID.randomUUID();
            saveName=uuid.toString()+"_"+originalName;

            //파일생성
            File saveFile=new File(URL+saveName);
            img.transferTo(saveFile);
        }

        StoreInformationVo StoreInformationVo = new StoreInformationVo();
        StoreInformationVo.setStoreId(storeIds);
        StoreInformationVo.setMenuName(name);
        StoreInformationVo.setMenuPrice(price);
        StoreInformationVo.setMenuImage(saveName);

        return storeService.menuedit(StoreInformationVo);

    }

    //메뉴 삭제하기
    @GetMapping("/menuedel")
    public int menudel(@RequestParam("id") int id,@RequestParam("name") String name){
        log.info(":::: 메뉴삭제하기 ::::");
       return storeService.menudel(id,name);
    }

    //주문 받기
    //상점아이디를 받아온다
    @PostMapping("/order")
    public List<OrderVo> order(@RequestBody OrderVo orderVo){
        log.info(":::: 주문알람 리스트 반환 ::::");
        return storeService.order(orderVo.getStoreId());

    }
    //조리하기
    @GetMapping("/cook")
    public int cook(@RequestParam("orderId") int id){
        log.info(":::: 음식점 조리 ::::");
        return storeService.cook(id);
    }

    //라이더 배정
    @GetMapping("/rider")
    public int rider(@RequestParam("orderId") int id){
        log.info(":::: 음식점에서 라이더 배정 ::::");
        return storeService.rider(id);
    }
    //주문 거절
    @GetMapping("/refuse")
    public int refuse(@RequestParam("orderId") int id){
        log.info(":::: 음식점에서 라이더 배정 ::::");
        return storeService.refuse(id);
    }

    //업체 주문 내역 불러오기
    @GetMapping("/orderReceipt")
    public List<StoreOrderInformationVo> orderReceipt(@RequestParam("store_id") int store_id){
        log.info("결제 내역 조회!" + store_id);
        return storeService.orderReceipt(store_id);
    }

    //매출 내역 그래프로 불러오기
    @GetMapping("/orderSales_info")
    public List<StoreOrderInformationVo> orderSales_info(@RequestParam("store_id") int store_id){
        log.info("현재 매출 내역 조회하기! " + store_id);
        return storeService.orderSales_info(store_id);
    }

    //업체 수정전 내용 받아오기
    @PostMapping("/store_edit_info")
    //받아올 데이터는 주인 아이디값
    public StoreRegistrationVo store_info(@RequestBody Map<String, Integer> data){
        log.info("업체 정보 불러오기" );
        int id=data.get("id");
        return storeService.store_info(id);



    }
    //업체수정
    @PostMapping("/store_edit")
    public ResponseEntity<?> store_edit(        @RequestParam("name") String name,
                                                @RequestParam("address") String address,
                                                @RequestParam("text") String text,
                                                @RequestParam(value = "img", required = false) MultipartFile img,
                                                @RequestParam("storeX") BigDecimal storeX,
                                                @RequestParam("storeY") BigDecimal storeY,
                                                @RequestParam("category") String category,
                                                @RequestParam("id") int id) throws IOException {

        StoreRegistrationVo storeRegistrationVo = new StoreRegistrationVo();
        //이미지 저장하기
        String saveName=null;
        if (img != null && !img.isEmpty()) {
            File uploadDir= new File(URL);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
                System.out.println("디렉토리 파일 생성");
            }

            //원본파일이름
            String originalName= img.getOriginalFilename();
            //파일 이름 UUID를 사용 하여 재정의
            UUID uuid = UUID.randomUUID();
            saveName=uuid.toString()+"_"+originalName;

            //파일생성
            File saveFile=new File(URL+saveName);
            img.transferTo(saveFile);

            storeRegistrationVo.setStore_image(saveName);
        }

        storeRegistrationVo.setStore_name(name);
        storeRegistrationVo.setStore_address(address);
        storeRegistrationVo.setStore_description(text);
        storeRegistrationVo.setStore_x(storeX);
        storeRegistrationVo.setStore_y(storeY);
        storeRegistrationVo.setStore_ca(category);
        //유저아이디 나중에는 받아와야한다.
        storeRegistrationVo.setOwner_id(id);

        int rs=storeService.store_edit(storeRegistrationVo);
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }


    }

    //업체 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> store_delete(@RequestBody Map<String, Integer> data){
        int store_id = data.get("store_id");
        int rs=storeService.store_delete(store_id);
        if (rs==1){
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

}