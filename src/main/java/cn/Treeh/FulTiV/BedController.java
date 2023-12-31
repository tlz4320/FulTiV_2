package cn.Treeh.FulTiV;

import cn.Treeh.FulTiV.Bed.BedIndex;
import cn.Treeh.FulTiV.Bed.Bin;
import cn.Treeh.FulTiV.utils.MConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

import static cn.Treeh.FulTiV.Bed.DecodeBed.decodeBed;
import static cn.Treeh.FulTiV.Util.byteMergerAll;
import static cn.Treeh.FulTiV.Util.fileToByte;
import static cn.Treeh.FulTiV.FulTiVApplication.index;

@Controller
public class BedController {
    @RequestMapping(value = "/bed/{filename}")
    public ResponseEntity<byte[]> getbedfile(HttpServletRequest request, @PathVariable("filename") String filename) throws Exception {
        String start = request.getHeader("start");
        String end = request.getHeader("end");
        String chr = request.getHeader("chr");
        chr = chr.substring(3);
        HttpHeaders headers = new HttpHeaders();
        if(start == null || end == null || start.equals("undefined") || end.equals("undefined"))
            return  new ResponseEntity<>(null, headers, HttpStatus.OK);
        if(start.length() == 0 || end.length() == 0)
            return  new ResponseEntity<>(null, headers, HttpStatus.OK);
        int startposition = Integer.parseInt(start);
        int endposition = Integer.parseInt(end);
        HashSet<Bin> set = index.getRegin(chr, startposition, endposition);
        byte[][] tempbytes = new byte[set.size()][];
        int i = 0;
        for(Bin bin : set){
            tempbytes[i++] = decodeBed(fileToByte(MConfig.prefix + "out2.txt",bin.getBegin() , bin.getBegin() + bin.getWidth() - 1));
        }
        //data/4/data
        byte[] res = byteMergerAll(tempbytes);
        return  new ResponseEntity<>(res, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/index/{filename}")
    public ResponseEntity<byte[]> getIndexfile(HttpServletRequest request,@PathVariable("filename") String filename) throws Exception {
        String chr = request.getHeader("chr");
        String start = request.getHeader("start");
        String end = request.getHeader("end");
        HttpHeaders headers = new HttpHeaders();
        if(chr == null || chr.length() == 0|| start == null || start.length() == 0 || end == null || end.length() == 0)
            return  new ResponseEntity<>(null, headers, HttpStatus.NOT_ACCEPTABLE);
        int startposition = Integer.parseInt(start);
        int endposition = Integer.parseInt(end);
        BedIndex index = new BedIndex();
        index.readIndex(MConfig.prefix + filename);
        HashSet<Bin> set = index.getRegin(chr, startposition, endposition);
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        boolean first = true;
        for(Bin b : set){
            if(first)
                first = !first;
            else
                builder.append(",");
            builder.append(b.toString());
        }
        builder.append("]");
        byte[] res = builder.toString().getBytes();
        return  new ResponseEntity<>(res, headers, HttpStatus.OK);
    }
}
