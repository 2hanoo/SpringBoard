package com.spring.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.common.CommandMap;
import com.spring.common.service.CommonService;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "commonService")
	private CommonService commonService;

	@RequestMapping(value = "/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception {
		Map<String, Object> map = commonService.selectFileInfo(commandMap.getMap());
		//commonService.selectFileInfo를 통해서 첨부파일의 정보를 가져온다.
		String storedFileName = (String) map.get("STORED_FILE_NAME");
		String originalFileName = (String) map.get("ORIGINAL_FILE_NAME");

		byte fileByte[] = FileUtils.readFileToByteArray(new File("D:\\Project\\file\\" + storedFileName));
		//commons-fileupload dependency의 FileUtils

		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\";");
		//attachment : 첨부파일
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);

		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

}
