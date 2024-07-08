package org.example.backend.service.storeReport;

import org.example.backend.service.StoreReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;


    //업체신고 서비스
    public int report(StoreReportVo storeReportVo){
        //그 주문번호로 신고한 적 이 있는지 확인
        int check=reportDao.reportCheck(storeReportVo);
        System.out.println(check);
        //신고한적없다면 신고가능
        if (check==0){
            int a=reportDao.report(storeReportVo);
            System.out.println(a);
            return a;
        }
        //10 이미 존재할때
        return 10;

    }

    public int check(StoreReportVo storeReportVo){
        //그 주문번호로 신고한 적 이 있는지 확인
        int check=reportDao.reportCheck(storeReportVo);
        System.out.println(check);
        //신고한적없다면 신고가능
        //10 이미 존재할때
        return check;

    }

}
