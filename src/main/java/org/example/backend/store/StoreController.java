package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.File;
import java.util.UUID;
import java.io.IOException;
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
//    private static final String URL="C:\\Users\\KOSTA\\Desktop\\finalfr\\public\\imgs\\";

    private static final String URL="C:\\Users\\kjk98\\OneDrive\\바탕 화면\\koster\\frontend\\public\\imgs\\";


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

        return storeService.menuRs(StoreInformationVo);

    }

    //메뉴 목록 불러오기
    @GetMapping("/menulist")
    public List<StoreInformationVo> menuList(@RequestParam("shopid") int id){
        return storeService.menuList(id);


    }

    //메뉴 수정하기
    @PostMapping("menuedit")
    public int menuedit(@RequestParam("name") String name,
                      @RequestParam("price") int price,
                        @RequestParam(value = "img", required = false) MultipartFile img,
                      @RequestParam("shopid") int storeIds) throws IOException {


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
       return storeService.menudel(id,name);


    }

}
