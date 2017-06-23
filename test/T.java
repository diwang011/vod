import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.BiConsumer;


public class T
{
    public static void main(String[] args)
    {
        /*
        String str ="锘縶\"IsSuccess\": true,\"PackageProcessingSummary\": {";
        System.out.println(str.indexOf("\""));
        System.out.println("{"+str.substring(str.indexOf("\"")));
        
        StringBuffer customerIds=new StringBuffer();
        List<Long> ids=new ArrayList<>();ids.add(1L);ids.add(44654L);
        if (ids.size()>0)
        {
            for (Long id : ids)
            {
                customerIds.append(id+",");
            }
            System.out.println(customerIds.toString());
        }
        System.out.println("end");
        
        String key="";
        String values="";
        Map<String, String> map1=new HashMap<String, String>();
        for (int i = 0; i < 1000000; i++)
        {
            map1.put("key"+i, "values"+i);
        }
        long startTime1=System.currentTimeMillis();
        for (Entry<String, String> map : map1.entrySet())
        {
            System.out.print("k : " + map.getKey() + " v : " + map.getValue());
        }
        long endTime1=System.currentTimeMillis();
        System.out.println();
        System.out.println("java 7 entrySet:"+(endTime1-startTime1)+"ms");
        
        long startTime2=System.currentTimeMillis();
        map1.forEach((k,v)->System.out.print("k : " + k + " v : " + v));
        long endTime2=System.currentTimeMillis();
        System.out.println();
        System.out.println("java 8 forEach:"+(endTime2-startTime2)+"ms");
        
        map1.forEach((k,v)->{
            if ("key1".equals(k))
            {
                System.out.println(v);
            }
        });
        
        */
        List<String> list =null;
//        for (int i = 0; i < 100; i++)
//        {
//            list.add("str"+i);
//        }
//        list.forEach((s)->{
//            if ("str1".equals(s))
//            {
//                System.out.println(s);
//            }
//        });
        for (String string : list)
        {
            System.out.println(string);
        }
        /*
        list.stream().filter(s-> s.contains("str1")).forEach(System.out::println);
        //Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );
        
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
            int result = e1.compareTo( e2 );
            return result;
        } );*/
        
//       Calendar cal=Calendar.getInstance();
//       cal.setTimeInMillis(1488416030000l);
//       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//       System.out.println(sdf.format(cal.getTime()));
//       cal.setTimeInMillis(1488417770162l);
//       System.out.println(sdf.format(cal.getTime()));
        
//        String str1 ="momni, momni.usrfl, momni.usrfl.usbsi, momni.usrfl.usbsi.all, momni.usrfl.ctadr, momni.usrfl.ctadr.all, momni.usrfl.bladr, momni.usrfl.bladr.all, momni.usrfl.spadr, momni.usrfl.spadr.all, momni.usrst, momni.usrst.all, momni.clsvc, momni.clsvc.all, momni.acinf, momni.acinf.all, momni.rechg, momni.rechg.rchst, momni.rechg.rchst.all, momni.rechg.rchdt, momni.rechg.rchdt.all, momni.rechg.rchbl, momni.rechg.rchbl.all, momni.rechg.bladj, momni.rechg.bladj.all, momni.msg, momni.msg.all, wrhs, wrhs.rcvng, wrhs.rcvng.rcvmg, wrhs.rcvng.rcvmg.all, wrhs.rcvng.recvn, wrhs.rcvng.recvn.all, wrhs.rcvng.putaw, wrhs.rcvng.putaw.all, wrhs.inadg, wrhs.inadg.adivt, wrhs.inadg.adivt.all, wrhs.inadg.rlctn, wrhs.inadg.rlctn.all, wrhs.inadg.adapr, wrhs.inadg.adapr.all, wrhs.inadg.qrywh, wrhs.inadg.qrywh.all, wrhs.inadg.hstry, wrhs.inadg.hstry.all, wrhs.inadg.chglg, wrhs.inadg.chglg.all, wrhs.whstg, wrhs.whstg.znmgt, wrhs.whstg.znmgt.all, wrhs.whstg.lcmgt, wrhs.whstg.lcmgt.all, wrhs.whrma, wrhs.whrma.all, wrhs.whrlb, wrhs.whrlb.rlbhd, wrhs.whrlb.rlbhd.all, wrhs.whrlb.rlbrp, wrhs.whrlb.rlbrp.all, shpmt, shpmt.shmgm, shpmt.shmgm.pipak, shpmt.shmgm.pipak.all, shpmt.shmgm.cfmpk, shpmt.shmgm.cfmpk.all, shpmt.shmgm.shlbl, shpmt.shmgm.shlbl.all, shpmt.shmgm.shcpl, shpmt.shmgm.shcpl.all, shpmt.shmgm.ajssp, shpmt.shmgm.ajssp.all, shpmt.shmgm.pikqr, shpmt.shmgm.pikqr.all, shpmt.shrpt, shpmt.shrpt.all, front, front.all, product, product.control, product.control.all, product.all, momni.subac, momni.subac.all, momni.subac.sbalt, momni.subac.sbalt.all, momni.subac.aumgm, momni.subac.aumgm.all, momni.subac.rsmgm, momni.subac.rsmgm.all, rcus, rcus.whau, rcus.whau.all, rcus.criau, rcus.criau.all, rcus.mtch, rcus.mtch.wcmch, rcus.mtch.wcmch.all, rcus.mtch.mcall, rcus.mtch.mcall.all, menu.momni, menu.momni.usrfl, menu.momni.usrfl.usbsi, menu.momni.usrfl.ctadr, menu.momni.usrfl.bladr, menu.momni.usrfl.spadr, menu.momni.usrst, menu.momni.clsvc, menu.momni.acinf, menu.momni.rechg, menu.momni.rechg.rchst, menu.momni.rechg.rchdt, menu.momni.rechg.rchbl, menu.momni.rechg.bladj, menu.momni.msg, menu.wrhs, menu.wrhs.rcvng, menu.wrhs.rcvng.rcvmg, menu.wrhs.rcvng.recvn, menu.wrhs.rcvng.putaw, menu.wrhs.inadg, menu.wrhs.inadg.adivt, menu.wrhs.inadg.rlctn, menu.wrhs.inadg.adapr, menu.wrhs.inadg.qrywh, menu.wrhs.inadg.hstry, menu.wrhs.inadg.chglg, menu.wrhs.whstg, menu.wrhs.whstg.znmgt, menu.wrhs.whstg.lcmgt, menu.wrhs.whrma, menu.wrhs.whrlb, menu.wrhs.whrlb.rlbhd, menu.wrhs.whrlb.rlbrp, menu.shpmt, menu.shpmt.shmgm, menu.shpmt.shmgm.pipak, menu.shpmt.shmgm.cfmpk, menu.shpmt.shmgm.shlbl, menu.shpmt.shmgm.shcpl, menu.shpmt.shmgm.ajssp, menu.shpmt.shmgm.pikqr, menu.shpmt.shrpt, menu.front, menu.rcus, menu.rcus.whau, menu.rcus.criau, menu.rcus.mtch, menu.rcus.mtch.wcmch, menu.rcus.mtch.mcall, menu.product, menu.product.control, menu.product.all, menu.front, front.all, menu.momni.subac, menu.momni.subac.sbalt, menu.momni.subac.aumgm, menu.momni.subac.rsmgm, menu.rcus, menu.rcus.whau, menu.rcus.criau, menu.rcus.mtch, menu.rcus.mtch.wcmch, menu.rcus.mtch.mcall, menu.shpmt.shmgm.patf, shpmt.shmgm.patf, shpmt.shmgm.patf.all, menu.momni.usrfl.usbpwd, menu.shpmt.shmgm.cfob, shpmt.shmgm.cfob, shpmt.shmgm.cfob.all, menu.shpmt.basecf.packcf, shpmt.basecf.packcf, shpmt.basecf.packcf.all, menu.shpmt.basecf, shpmt.basecf, shpmt.basecf.all, menu.shpmt.basecf.patfcf, shpmt.basecf.patfcf, shpmt.basecf.patfcf.all, menu.shpmt.shmgm.sorter, shpmt.shmgm.sorter, shpmt.shmgm.sorter.all, menu.shpmt.shmgm.scan, shpmt.shmgm.scan, shpmt.shmgm.scan.all, menu.wrhs.rcvng.fastonshelf, wrhs.rcvng.fastonshelf, wrhs.rcvng.fastonshelf.all,menu.shpmt.shmgm.shippingreport, shpmt.shmgm.shippingreport, shpmt.shmgm.shippingreport.all, menu.wrhs.inadg.loadapr, wrhs.inadg.loadapr, wrhs.inadg.loadapr.all,menu.shpmt.shmgm.printlabellog, shpmt.shmgm.printlabellog, shpmt.shmgm.printlabellog.all";
//        String str2 ="momni, momni.usrfl, momni.usrfl.usbsi, momni.usrfl.usbsi.all, momni.usrfl.ctadr, momni.usrfl.ctadr.all, momni.usrfl.bladr, momni.usrfl.bladr.all, momni.usrfl.spadr, momni.usrfl.spadr.all, momni.usrst, momni.usrst.all, momni.clsvc, momni.clsvc.all, momni.acinf, momni.acinf.all, momni.rechg, momni.rechg.rchst, momni.rechg.rchst.all, momni.rechg.rchdt, momni.rechg.rchdt.all, momni.rechg.rchbl, momni.rechg.rchbl.all, momni.rechg.bladj, momni.rechg.bladj.all, momni.rechg.balancelock, momni.rechg.balancelock.all, momni.msg, momni.msg.all, momni.subac, momni.subac.aumgm, momni.subac.aumgm.all, momni.subac.sbalt, momni.subac.sbalt.all, momni.subac.rsmgm, momni.subac.rsmgm.all,wrhs, wrhs.rcvng.rcvmg, wrhs.rcvng.rcvmg.all, wrhs.rcvng.recvn, wrhs.rcvng.recvn.all, wrhs.rcvng.putaw, wrhs.rcvng.putaw.all, wrhs.rcvng.rmsign, wrhs.rcvng.rmsign.all, wrhs.rcvng.fastonshelf, wrhs.rcvng.fastonshelf.all, wrhs.inadg.adivt, wrhs.inadg.adivt.all, wrhs.inadg.rlctn, wrhs.inadg.rlctn.all, wrhs.inadg.adapr, wrhs.inadg.adapr.all, wrhs.inadg.qrywh, wrhs.inadg.qrywh.all, wrhs.inadg.hstry, wrhs.inadg.hstry.all, wrhs.inadg.chglg, wrhs.inadg.chglg.all, wrhs.whstg, wrhs.whstg.znmgt, wrhs.whstg.znmgt.all, wrhs.whstg.lcmgt, wrhs.whstg.lcmgt.all, wrhs.whrlb, wrhs.whrlb.rlbhd, wrhs.whrlb.rlbhd.all, wrhs.whrlb.rlbrp, wrhs.whrlb.rlbrp.all, shpmt, shpmt.shmgm, shpmt.shmgm.pipak, shpmt.shmgm.pipak.all, shpmt.shmgm.cfmpk, shpmt.shmgm.cfmpk.all, shpmt.shmgm.shlbl, shpmt.shmgm.shlbl.all, shpmt.shmgm.shcpl, shpmt.shmgm.shcpl.all, shpmt.shmgm.ajssp, shpmt.shmgm.ajssp.all, shpmt.shmgm.pikqr, shpmt.shmgm.pikqr.all, shpmt.shmgm.patf, shpmt.shmgm.patf.all, shpmt.shmgm.cfob, shpmt.shmgm.cfob.all, shpmt.shmgm.sorter, shpmt.shmgm.sorter.all, shpmt.shmgm.scan, shpmt.shmgm.scan.all, shpmt.shmgm.shippingreport, shpmt.shmgm.shippingreport.all, shpmt.shrpt, shpmt.shrpt.all, shpmt.basecf, shpmt.basecf.all, shpmt.basecf.packcf, shpmt.basecf.packcf.all, shpmt.basecf.patfcf, shpmt.basecf.patfcf.all, rcus.mtch.sign, rcus.mtch.sign.all, product, product.control, product.control.all, product.all, momni.subac, momni.subac.all, momni.subac.sbalt, momni.subac.sbalt.all, momni.subac.aumgm, momni.subac.aumgm.all, momni.subac.rsmgm, momni.subac.rsmgm.all, rcus, rcus.whau, rcus.whau.all, rcus.criau, rcus.criau.all, rcus.mtch, rcus.mtch.wcmch, rcus.mtch.wcmch.all, rcus.mtch.mcall, rcus.mtch.mcall.all, menu.momni, menu.momni.usrfl, menu.momni.usrfl.usbsi, menu.momni.usrfl.ctadr, menu.momni.usrfl.bladr, menu.momni.usrfl.spadr, menu.momni.usrst, menu.momni.clsvc, menu.momni.acinf, menu.momni.rechg, menu.momni.rechg.rchst, menu.momni.rechg.rchdt, menu.momni.rechg.rchbl, menu.momni.rechg.bladj, menu.momni.rechg.balancelock, menu.momni.msg, menu.momni.subac, menu.momni.subac.aumgm, menu.momni.subac.sbalt, menu.momni.subac.rsmgm, menu.wrhs, menu.wrhs.rcvng, menu.wrhs.rcvng.rcvmg, menu.wrhs.rcvng.recvn, menu.wrhs.rcvng.putaw, menu.wrhs.rcvng.bmdsz, menu.wrhs.rcvng.rmsign, menu.wrhs.rcvng.fastonshelf, menu.wrhs.inadg, menu.wrhs.inadg.adivt, menu.wrhs.inadg.rlctn, menu.wrhs.inadg.adapr, menu.wrhs.inadg.qrywh, menu.wrhs.inadg.hstry, menu.wrhs.inadg.chglg, menu.wrhs.whstg, menu.wrhs.whstg.znmgt, menu.wrhs.whstg.lcmgt, menu.wrhs.whrma, menu.wrhs.whrlb, menu.wrhs.whrlb.rlbhd, menu.wrhs.whrlb.rlbrp, menu.wrhs.lgstmg, menu.shpmt, menu.shpmt.shmgm, menu.shpmt.shmgm.pipak, menu.shpmt.shmgm.cfmpk, menu.shpmt.shmgm.shlbl, menu.shpmt.shmgm.shcpl, menu.shpmt.shmgm.ajssp, menu.shpmt.shmgm.pikqr, menu.shpmt.shmgm.patf, menu.shpmt.shmgm.cfob, menu.shpmt.shmgm.sorter, menu.shpmt.shmgm.scan, menu.shpmt.shmgm.shippingreport, menu.shpmt.shrpt, menu.shpmt.basecf, menu.shpmt.basecf.packcf, menu.shpmt.basecf.patfcf, menu.front, menu.front.all, menu.rcus, menu.rcus.whau, menu.rcus.criau, menu.rcus.mtch, menu.rcus.mtch.wcmch, menu.rcus.mtch.mcall, menu.rcus.mtch.sign, menu.rcus.cnlau, menu.rcus.rlmg, menu.rcus.rlmg.dtrwh, menu.rcus.rlmg.dtrsp, menu.rcus.ordersource.other, menu.rcus.ordersource.channel, menu.product, menu.product.control, menu.product.all, menu.front, front.all, menu.momni.subac, menu.momni.subac.sbalt, menu.momni.subac.aumgm, menu.momni.subac.rsmgm, menu.rcus, menu.rcus.whau, menu.rcus.criau, menu.rcus.mtch, menu.rcus.mtch.wcmch, menu.rcus.mtch.mcall, menu.wrhs.inadg.loadapr, wrhs.inadg.loadapr, wrhs.inadg.loadapr.all,menu.shpmt.basecf.packcf, shpmt.basecf.packcf, shpmt.basecf.packcf.all, menu.shpmt.basecf, shpmt.basecf, shpmt.basecf.all, menu.shpmt.basecf.patfcf, shpmt.basecf.patfcf, shpmt.basecf.patfcf.all,menu.shpmt.shmgm.printlabellog, shpmt.shmgm.printlabellog, shpmt.shmgm.printlabellog.all";
//        String[] arr1=str1.split(",");
//        String[] arr2=str2.split(",");
//        for (String s1 : arr1)
//        {
//          s1=  s1.trim();
//            if (s1.indexOf("wrhs")!=-1)
//            {
//                boolean flag=false;
//                for (String s2 : arr2)
//                {
//                    s2=  s2.trim();
//                    if (s1.equals(s2))
//                    {
//                        flag=true;
//                        break;
//                    }
//                }
//                if (!flag)
//                {
//                    System.out.println(s1);
//                }
//            }
//        }
//        List<String> trackingNums=new ArrayList<>();
//        trackingNums.add(null);
//        
//        System.out.println(trackingNums.get(0));
       
//        for (int i = 0; i < 100; i++)
//        {
//            new Thread(new Runnable()
//            {
//                
//                @Override
//                public void run()
//                {
//                    System.out.println(new Random().nextInt(20) );
//                    
//                }
//            }).start();
//        }
//        java.util.BitSet bitSet = new java.util.BitSet(10000000);  
//        //将指定位的值设为true  
//        bitSet.set(9999, true);  
//        //输出指定位的值  
//        System.out.println(bitSet.get(9999));  
//        System.out.println(bitSet.get(9998));  

    }

}
