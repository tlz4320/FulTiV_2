package cn.Treeh.FulTiV;

import cn.Treeh.FulTiV.Bed.BedIndex;
import cn.Treeh.FulTiV.utils.MConfig;
import htsjdk.samtools.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

import static cn.Treeh.FulTiV.Util.fileToByte;
import static cn.Treeh.FulTiV.Util.makefinaldata;


@SpringBootApplication
@Controller
public class FulTiVApplication {
    static public ImportCluster cluster;
    static public BedIndex index;
    static public LinkedList<String> samplename;
	public static void main(String[] args) {
        cluster = new ImportCluster(MConfig.prefix +"result.txt");
//        cluster.loadData();
        index = new BedIndex();
        //index.readIndex(MConfig.prefix +"out2.txt.bdx");
        File file = new File(MConfig.prefix);
        samplename = new LinkedList<>();
//        File[] files = file.listFiles();
//        for(File f : files){
//            if(f.toString().endsWith(".bam")){
//                samplename.add(f.getName());
//            }
//        }
//        reads2sample = new ImportReads();
//        reads2sample.loadData("D:\\program\\data\\4\\data\\sample");
		SpringApplication.run(FulTiVApplication.class, args);
	}

//chr22:17,727,296-17,765,894








}
