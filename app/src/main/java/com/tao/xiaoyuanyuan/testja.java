package com.tao.xiaoyuanyuan;

import com.tao.xiaoyuanyuan.recoreddhistory.bean.DateRecodBean;
import com.tao.xiaoyuanyuan.utils.DateUtils;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: testja
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/4/24 15:17
 */
public class testja {
       //中央经线 是多少？？？？
       //保留代号结果会正确 代号是多少呢？？
       //投影带 是多少
      //37.203267527777776,115.40381543333334,3
    public static void main(String[] args) {

        for (int i = 1; i < 8; i++) {
            String[] b1 =DateUtils.getB1Time(i);
            System.out.println(b1[0]+"===="+b1[1]);
            String diffTime = DateUtils.getTimeStringDifference(DateUtils.getDate(b1[0]), DateUtils.getDate(b1[1]));
            System.out.println("时差："+diffTime+"");

        }


    }

}
