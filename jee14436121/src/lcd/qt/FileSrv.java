package lcd.qt;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lcd.Pojo.Userscore;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;


	@IocBean
	@At("/file")
	@Filters({@By(type=LoginFilter.class)})
	public class FileSrv {
	    @Inject
	    protected UploadAdaptor upload;
	    /**
	     * 根据异常提示错误信息
	     * @param t
	     */
	    private String errorMsg(Throwable t) {
	        if (t == null || t.getClass() == null) {
	            return "错误：未知system错误！";
	        } else {
	            String className = t.getClass().getSimpleName();
	            if (className.equals("UploadUnsupportedFileNameException")) {
	                String name = upload.getContext().getNameFilter();
	                return "错误：无效的文件扩展名，支持的扩展名：" + name.substring(name.indexOf("(") + 1, name.lastIndexOf(")")).replace("|", ",");
	            } else if (className.equals("UploadUnsupportedFileTypeException")) {
	                return "错误：不支持的文件类型！";
	            } else if (className.equals("UploadOutOfSizeException")) {
	                return "错误：文件超出" + getFileSize(upload.getContext().getMaxFileSize(), 2) + "MB";
	            } else if (className.equals("UploadStopException")) {
	                return "错误：上传中断！";
	            } else {
	                return "错误：未知错误！";
	            }
	        }
	    }
	    public String jsonString(String str)
	    {
	        str = str.replace("\\", "\\\\");
	        str = str.replace("/", "\\/");
	        str = str.replace("'", "\\'");
	        return str;
	    }
	    /**
	     * 返回文件大小，单位MB
	     * @param filesize
	     * @param scale,小数位数
	     * @return
	     */
	    private double getFileSize(long filesize, int scale) {
	        BigDecimal bd1 = new BigDecimal(Long.toString(filesize));
	        BigDecimal bd2 = new BigDecimal(Long.toString(1024));
	        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
	  //{"${app.root}/temp", "8192", "utf-8", "200", "10485760"}
	  //参数: 临时文件夹路径,缓存环的大小,编码,临时文件夹的文件数,上传文件的最大大小
	    @AdaptBy(type = UploadAdaptor.class, args ="ioc:upload")
	    @POST
	   @At
	    @Ok("raw")
	    @Fail("void")
	    public String upload(@Param("filedata") TempFile tmpFile,AdaptorErrorContext errCtx) {
	      String err = "";
	        String msg = "''";  
	        if(tmpFile==null){
	        return "{'err':'" + jsonString("空文件") + "','msg':" + msg + "}";
	        }
	        /* //抛出异常后，不能返回前端*/
	        if (errCtx != null && errCtx.getAdaptorErr() != null) {
	              err=errorMsg(errCtx.getAdaptorErr());
	            return "{'err':'" + jsonString(err) + "','msg':" + msg + "}";
	            }
	        String filename = tmpFile.getSubmittedFileName();//本地文件名
	        long filelen=tmpFile.getSize();//文件大小
	        long maxfsize=10485760;//限制上传的文件大小<=10MB
	        if(filelen>maxfsize)return "{'err':'" + jsonString("文件超出" + getFileSize(maxfsize, 2) + "MB") + "','msg':" + msg + "}";
	 
	        String suffixname =tmpFile.getMeta().getFileExtension();
	        String date = DateUtil.getToday();//.replace("-", "/");
	        String uuid = UUID.randomUUID().toString().replaceAll("-", "");//32位十六进制小写
	        String fname = uuid + suffixname;
	        String newfilepath = Mvcs.getServletContext().getRealPath("/upload/" +  date+ "/");
	        Files.createDirIfNoExists(newfilepath);
	        String dest = newfilepath+ "/"+fname;//目标路径，含文件名
	        try {
	        tmpFile.write(dest);//存储到服务器Disk
	        } catch (IOException e) {
	            e.printStackTrace();
	            err="错误：文件服务器IO异常！";
	            return "{'err':'" + jsonString(err) + "','msg':" + msg + "}";
	        }
	 
	        String target="upload/" + date + "/" + fname;
	        msg="{'url':'"+target+"','localname':'"+jsonString(filename)+"','id':'1'}";
	        err="";
	        return  "{'err':'" + jsonString(err) + "','msg':" + msg + "}";
	    }
	    
	    public static void score2Xls(HttpServletResponse response,String fname,List<Userscore> sc)
	            throws WriteException,IOException{       
	      String fname2 = fname;
	      WritableWorkbook workbook=null;
	      OutputStream os=null;
	      try{
	        os = response.getOutputStream();//取得输出流
	        response.reset();//清空输出流       
	        //下面是对中文文件名的处理
	        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
	        fname = java.net.URLEncoder.encode(fname,"UTF-8");
	        fname=new String(fname.getBytes("UTF-8"),"ISO-8859-1");
	        response.setHeader("Content-Disposition","attachment;filename="+fname+".xls");
	        response.setContentType("application/msexcel");//定义输出类型
	      //创建工作薄
	        workbook = Workbook.createWorkbook(os);
	        //创建新的一页
	        WritableSheet sheet = workbook.createSheet("scores",0);
	      //构造表头
	        sheet.mergeCells(0, 0, 4, 0);//添加合并单元格，第一个参数是起始列，第二个参数是起始行，第三个参数是终止列，第四个参数是终止行
	        WritableFont bold = new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
	        WritableCellFormat titleFormate = new WritableCellFormat(bold);//生成一个单元格样式控制对象
	        titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
	        titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
	        titleFormate.setBorder(Border.ALL, BorderLineStyle.THIN);
	        Label title = new Label(0,0,fname2,titleFormate);
	        sheet.setRowView(0, 600, false);//设置第一行的高度
	        sheet.addCell(title);
	        sheet.setColumnView(0,18);
	        sheet.setColumnView(1,14);
	        sheet.setColumnView(2,14);
	        sheet.setColumnView(3,14);
	        sheet.setColumnView(4,14);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容       
	        Label ksid = new Label(0,1,"考试编号",titleFormate);
	        sheet.addCell(ksid);
	        Label bj = new Label(1,1,"部门",titleFormate);
	        sheet.addCell(bj);
	        Label uid = new Label(2,1,"考号",titleFormate);
	        sheet.addCell(uid);
	        Label xm = new Label(3,1,"姓名",titleFormate);
	        sheet.addCell(xm);
	        Label score = new Label(4,1,"成绩",titleFormate);
	        sheet.addCell(score);     
	        //添加带有formatting的Number对象  
	        //NumberFormat nf = new NumberFormat("0.0");  
	        WritableCellFormat wcfN = new WritableCellFormat();
	        wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);
	        int rows=sc.size();
	        for(int i=0;i<rows;i++)
	        {
	            Userscore s=sc.get(i);
	            sheet.addCell(new Label(0,i+2,s.getKsid(),wcfN));
	            sheet.addCell(new Label(1,i+2,s.getDpt(),wcfN));
	            sheet.addCell(new Label(2,i+2,s.getUid(),wcfN));
	            sheet.addCell(new Label(3,i+2,s.getUname(),wcfN));
	            sheet.addCell(new jxl.write.Number(4,i+2,s.getScore(),wcfN));
	        }
	        workbook.write();
	      } catch (Exception e) { 
	               e.printStackTrace(); 
	            }
	    finally{
	        if(workbook!=null){
	            try {
	            workbook.close();
	            os.close();//关闭此输出流并释放与此流有关的所有系统资源。
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    }
	    
	    
	    
	}

