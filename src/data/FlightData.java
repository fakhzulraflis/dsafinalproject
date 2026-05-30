package data;

import com.datastruct.Graph;
import java.util.HashMap;
import model.Airport;

public class FlightData {

    public static HashMap<String, Airport> airports = new HashMap<>();

    public static void load(Graph<Airport> graph) {

        // List Kota
        Airport AMQ = new Airport("AMQ");
        Airport BPN = new Airport("BPN");
        Airport BTJ = new Airport("BTJ");
        Airport TKG = new Airport("TKG");
        Airport BDJ = new Airport("BDJ");
        Airport BWX = new Airport("BWX");
        Airport BTH = new Airport("BTH");
        Airport BUW = new Airport("BUW");
        Airport BKS = new Airport("BKS");
        Airport BIK = new Airport("BIK");
        Airport DPS = new Airport("DPS");
        Airport GTO = new Airport("GTO");
        Airport CGK = new Airport("CGK");
        Airport DJB = new Airport("DJB");
        Airport DJJ = new Airport("DJJ");
        Airport DHX = new Airport("DHX");
        Airport KDI = new Airport("KDI");
        Airport KOE = new Airport("KOE");
        Airport LBJ = new Airport("LBJ");
        Airport LUV = new Airport("LUV");
        Airport LOP = new Airport("LOP");
        Airport LUW = new Airport("LUW");
        Airport UPG = new Airport("UPG");
        Airport MDC = new Airport("MDC");
        Airport MKW = new Airport("MKW");
        Airport KNO = new Airport("KNO");
        Airport MKQ = new Airport("MKQ");
        Airport PDG = new Airport("PDG");
        Airport PKY = new Airport("PKY");
        Airport PLM = new Airport("PLM");
        Airport PLW = new Airport("PLW");
        Airport PGK = new Airport("PGK");
        Airport PKU = new Airport("PKU");
        Airport PNK = new Airport("PNK");
        Airport AAP = new Airport("AAP");
        Airport SRG = new Airport("SRG");
        Airport DTB = new Airport("DTB");
        Airport SKJ = new Airport("SKJ");
        Airport SOC = new Airport("SOC");
        Airport SOQ = new Airport("SOQ");
        Airport SUB = new Airport("SUB");
        Airport TJQ = new Airport("TJQ");
        Airport TRK = new Airport("TRK");
        Airport TTE = new Airport("TTE");
        Airport TIM = new Airport("TIM");
        Airport YIA = new Airport("YIA");
        Airport WGP = new Airport("WGP");

        // Simpan Kode IATA ke hash map
        airports.put("AMQ", AMQ);
        airports.put("BPN", BPN);
        airports.put("BTJ", BTJ);
        airports.put("TKG", TKG);
        airports.put("BDJ", BDJ);
        airports.put("BWX", BWX);
        airports.put("BTH", BTH);
        airports.put("BUW", BUW);
        airports.put("BKS", BKS);
        airports.put("BIK", BIK);
        airports.put("DPS", DPS);
        airports.put("GTO", GTO);
        airports.put("CGK", CGK);
        airports.put("DJB", DJB);
        airports.put("DJJ", DJJ);
        airports.put("DHX", DHX);
        airports.put("KDI", KDI);
        airports.put("KOE", KOE);
        airports.put("LBJ", LBJ);
        airports.put("LUV", LUV);
        airports.put("LOP", LOP);
        airports.put("LUW", LUW);
        airports.put("UPG", UPG);
        airports.put("MDC", MDC);
        airports.put("MKW", MKW);
        airports.put("KNO", KNO);
        airports.put("MKQ", MKQ);
        airports.put("PDG", PDG);
        airports.put("PKY", PKY);
        airports.put("PLM", PLM);
        airports.put("PLW", PLW);
        airports.put("PGK", PGK);
        airports.put("PKU", PKU);
        airports.put("PNK", PNK);
        airports.put("AAP", AAP);
        airports.put("SRG", SRG);
        airports.put("DTB", DTB);
        airports.put("SKJ", SKJ);
        airports.put("SOC", SOC);
        airports.put("SOQ", SOQ);
        airports.put("SUB", SUB);
        airports.put("TJQ", TJQ);
        airports.put("TRK", TRK);
        airports.put("TTE", TTE);
        airports.put("TIM", TIM);
        airports.put("YIA", YIA);
        airports.put("WGP", WGP);

        // Flight Direction :
        // AMQ
        graph.addEdge(AMQ, CGK, 3689290);
        graph.addEdge(AMQ, UPG, 1958715);
        graph.addEdge(AMQ, LUV, 1520371);
        graph.addEdge(AMQ, SOQ, 1240011);
        graph.addEdge(AMQ, SUB, 2752816);

        // BPN
        graph.addEdge(BPN, DPS, 1788183);
        graph.addEdge(BPN, CGK, 1999814);
        graph.addEdge(BPN, UPG, 1363133);
        graph.addEdge(BPN, SRG, 1931203);
        graph.addEdge(BPN, SUB, 1704645);
        graph.addEdge(BPN, YIA, 1664043);

        // BTJ
        graph.addEdge(BTJ, KNO, 1191642);

        // TKG
        graph.addEdge(TKG, BTH, 1667431);
        graph.addEdge(TKG, CGK, 689264);

        // BDJ
        graph.addEdge(BDJ, BPN, 1758279);
        graph.addEdge(BDJ, DPS, 1502264);
        graph.addEdge(BDJ, CGK, 1546715);
        graph.addEdge(BDJ, UPG, 1636751);
        graph.addEdge(BDJ, SRG, 1479862);
        graph.addEdge(BDJ, SUB, 1221914);
        graph.addEdge(BDJ, YIA, 1681504);

        // BWX
        graph.addEdge(BWX, CGK, 1777741);

        // BTH
        graph.addEdge(BTH, CGK, 1561039);
        graph.addEdge(BTH, KNO, 1253043);
        graph.addEdge(BTH, PDG, 1152299);
        graph.addEdge(BTH, PLM, 1243495);
        graph.addEdge(BTH, PKU, 890540);
        graph.addEdge(BTH, PNK, 1532001);
        graph.addEdge(BTH, SRG, 2009858);
        graph.addEdge(BTH, SUB, 2044168);
        graph.addEdge(BTH, YIA, 2064059);

        // BUW
        graph.addEdge(BUW, UPG, 1613749);

        // BKS
        graph.addEdge(BKS, CGK, 1403010);

        // BIK
        graph.addEdge(BIK, DJJ, 1365521);

        // DPS
        graph.addEdge(DPS, BPN, 1784005);
        graph.addEdge(DPS, BDJ, 1557658);
        graph.addEdge(DPS, CGK, 1901257);
        graph.addEdge(DPS, KOE, 1909312);
        graph.addEdge(DPS, LOP, 683093);
        graph.addEdge(DPS, UPG, 1579537);
        graph.addEdge(DPS, SRG, 1510119);
        graph.addEdge(DPS, SOC, 1335089);
        graph.addEdge(DPS, SUB, 901352);
        graph.addEdge(DPS, YIA, 1503557);

        // GTO
        graph.addEdge(GTO, CGK, 2753845);
        graph.addEdge(GTO, UPG, 1846760);

        // CGK
        graph.addEdge(CGK, KNO, 1762416);
        graph.addEdge(CGK, BTH, 1645775);
        graph.addEdge(CGK, SUB, 1341237);
        graph.addEdge(CGK, UPG, 2195800);
        graph.addEdge(CGK, BPN, 1955618);
        graph.addEdge(CGK, PDG, 1618431);
        graph.addEdge(CGK, PLM, 886332);
        graph.addEdge(CGK, PNK, 1459468);
        graph.addEdge(CGK, PKU, 1655270);
        graph.addEdge(CGK, TKG, 812056);
        graph.addEdge(CGK, DJB, 1410174);
        graph.addEdge(CGK, PGK, 1082338);
        graph.addEdge(CGK, TJQ, 1114341);
        graph.addEdge(CGK, DTB, 2032382);
        graph.addEdge(CGK, BWX, 1728132);
        graph.addEdge(CGK, LOP, 1455680);
        graph.addEdge(CGK, BTJ, 2759817);
        graph.addEdge(CGK, BKS, 1404995);
        graph.addEdge(CGK, DHX, 1186785);

        // DJJ
        graph.addEdge(DJJ, UPG, 3266610);
        graph.addEdge(DJJ, SOQ, 1828710);
        graph.addEdge(DJJ, MKQ, 1525387);
        graph.addEdge(DJJ, TIM, 1173830);

        // DHX
        graph.addEdge(DHX, CGK, 1315937);

        // KDI
        graph.addEdge(KDI, CGK, 2311338);
        graph.addEdge(KDI, UPG, 1017490);

        // KOE
        graph.addEdge(KOE, DPS, 1884689);
        graph.addEdge(KOE, CGK, 1805656);
        graph.addEdge(KOE, SUB, 1957846);
        graph.addEdge(KOE, WGP, 1572314);

        // LBJ
        graph.addEdge(LBJ, KOE, 1557261);
        graph.addEdge(LBJ, LOP, 1791168);

        // LOP
        graph.addEdge(LOP, DPS, 698438);
        graph.addEdge(LOP, CGK, 1837907);
        graph.addEdge(LOP, LBJ, 1661445);
        graph.addEdge(LOP, SUB, 1057722);
        graph.addEdge(LOP, YIA, 1613350);
        graph.addEdge(LOP, WGP, 1717037);

        // LUW
        graph.addEdge(LUW, MDC, 1653031);

        // UPG
        graph.addEdge(UPG, AMQ, 2028166);
        graph.addEdge(UPG, BPN, 1366614);
        graph.addEdge(UPG, BDJ, 1627371);
        graph.addEdge(UPG, BUW, 975458);
        graph.addEdge(UPG, BIK, 2993798);
        graph.addEdge(UPG, DPS, 1584093);
        graph.addEdge(UPG, GTO, 1894377);
        graph.addEdge(UPG, CGK, 2162512);
        graph.addEdge(UPG, DJJ, 3648399);
        graph.addEdge(UPG, KDI, 1035128);
        graph.addEdge(UPG, MDC, 1920760);
        graph.addEdge(UPG, MKQ, 3606927);
        graph.addEdge(UPG, PLW, 1423300);
        graph.addEdge(UPG, SRG, 1785497);
        graph.addEdge(UPG, SOQ, 2129993);
        graph.addEdge(UPG, SUB, 1636522);
        graph.addEdge(UPG, TTE, 1907335);
        graph.addEdge(UPG, TIM, 3084814);
        graph.addEdge(UPG, YIA, 1800425);

        // MDC
        graph.addEdge(MDC, LUW, 1570606);
        graph.addEdge(MDC, UPG, 1878206);
        graph.addEdge(MDC, SOQ, 1647979);
        graph.addEdge(MDC, SUB, 2665852);
        graph.addEdge(MDC, TTE, 1312712);

        // MKW
        graph.addEdge(MKW, UPG, 2621477);
        graph.addEdge(MKW, SOQ, 976074);

        // KNO
        graph.addEdge(KNO, BTJ, 1211422);
        graph.addEdge(KNO, BTH, 1150708);
        graph.addEdge(KNO, CGK, 1836615);
        graph.addEdge(KNO, PDG, 1407190);
        graph.addEdge(KNO, PKU, 1242499);

        // MKQ
        graph.addEdge(MKQ, DJJ, 1461391);
        graph.addEdge(MKQ, UPG, 3532441);

        // PDG
        graph.addEdge(PDG, BTH, 1237129);
        graph.addEdge(PDG, CGK, 1808769);
        graph.addEdge(PDG, KNO, 1381830);

        // PKY
        graph.addEdge(PKY, BPN, 1815633);
        graph.addEdge(PKY, CGK, 1622095);
        graph.addEdge(PKY, SRG, 1507237);
        graph.addEdge(PKY, SUB, 1259904);
        graph.addEdge(PKY, YIA, 1732492);

        // PLM
        graph.addEdge(PLM, BTH, 1150480);
        graph.addEdge(PLM, CGK, 941698);
        graph.addEdge(PLM, KNO, 1954563);
        graph.addEdge(PLM, PGK, 674562);
        graph.addEdge(PLM, SUB, 1657676);
        graph.addEdge(PLM, YIA, 1870029);

        // PLW
        graph.addEdge(PLW, UPG, 1413292);
        graph.addEdge(PLW, SUB, 1990992);

        // PGK
        graph.addEdge(PGK, CGK, 1105443);
        graph.addEdge(PGK, TJQ, 725417);

        // PKU
        graph.addEdge(PKU, CGK, 1711438);
        graph.addEdge(PKU, BTH, 919931);
        graph.addEdge(PKU, KNO, 1261268);

        // PNK
        graph.addEdge(PNK, CGK, 1478459);
        graph.addEdge(PNK, SUB, 1681757);
        graph.addEdge(PNK, BTH, 1545151);
        graph.addEdge(PNK, SRG, 1824012);

        // AAP
        graph.addEdge(AAP, SUB, 1708344);

        // SRG
        graph.addEdge(SRG, PNK, 1813989);
        graph.addEdge(SRG, BDJ, 1558319);
        graph.addEdge(SRG, BPN, 1920436);

        // DTB
        graph.addEdge(DTB, CGK, 2043075);

        // SKJ
        graph.addEdge(SKJ, CGK, 1744525);

        // SOC
        graph.addEdge(SOC, DPS, 1345625);

        // SOQ
        graph.addEdge(SOQ, MKW, 1019989);
        graph.addEdge(SOQ, DJJ, 1838579);
        graph.addEdge(SOQ, UPG, 2023983);

        // SUB
        graph.addEdge(SUB, CGK, 1354521);
        graph.addEdge(SUB, DPS, 796104);
        graph.addEdge(SUB, BPN, 1738229);
        graph.addEdge(SUB, UPG, 1722854);
        graph.addEdge(SUB, AMQ, 2827745);
        graph.addEdge(SUB, BDJ, 1214167);
        graph.addEdge(SUB, LOP, 1091351);
        graph.addEdge(SUB, LBJ, 1880483);
        graph.addEdge(SUB, PKY, 1398499);
        graph.addEdge(SUB, AAP, 1817089);
        graph.addEdge(SUB, TRK, 2341434);
        graph.addEdge(SUB, KOE, 2047355);

        // TJQ
        graph.addEdge(TJQ, PGK, 700991);

        // TRK
        graph.addEdge(TRK, BPN, 1352815);
        graph.addEdge(TRK, SUB, 2313342);

        // TTE
        graph.addEdge(TTE, UPG, 1848074);
        graph.addEdge(TTE, MDC, 1412989);

        // TIM
        graph.addEdge(TIM, UPG, 2856569);
        graph.addEdge(TIM, DJJ, 1204296);

        // YIA
        graph.addEdge(YIA, BPN, 1858368);
        graph.addEdge(YIA, BDJ, 1716139);
        graph.addEdge(YIA, DPS, 1541127);
        graph.addEdge(YIA, LOP, 1671299);
        graph.addEdge(YIA, UPG, 1931232);
        graph.addEdge(YIA, KNO, 2731269);
        graph.addEdge(YIA, BTH, 2095550);
        graph.addEdge(YIA, PKY, 1825722);
        graph.addEdge(YIA, PLM, 1921736);
        graph.addEdge(YIA, PKU, 2307043);

        // WGP
        graph.addEdge(WGP, LOP, 1724137);
        graph.addEdge(WGP, KOE, 1612592);
    }
}
