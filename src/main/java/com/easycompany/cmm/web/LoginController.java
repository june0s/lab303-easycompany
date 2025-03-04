package com.easycompany.cmm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.easycompany.cmm.service.LoginService;
import com.easycompany.cmm.service.SampleService;
import com.easycompany.cmm.vo.Account;

@Controller
@PropertySource("classpath:/property/db.properties")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SampleService sampleService;

    private final Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    Environment env;

    /**
     * 사용자로 부터 아이디, 패스워드를 입력받아 인증 성공이면 세션 객체에 계정정보를 담고 사원정보리스트 페이지로 포워딩한다. 인증에
     * 실패하면 로그인 페이지로 다시 리턴한다.
     */
    @RequestMapping("/loginProcess.do")
    public String login(HttpServletRequest request, @RequestParam("id") String id,
            @RequestParam("password") String password) {

        log.info("+ db.url = " + env.getProperty("db.url"));
        log.info("+ db.username = " + env.getProperty("db.username"));
//        log.info("+ db.password = " + env.getProperty("db.password"));

        log.info("+ login() id = " + id + ", pw = " + password);

        // v [Step 4-1-01] LoginService의 authenticate메소드를 이용하여
    	// 로긴여부 체크 Account 객체를 리턴 받는다.
        Account account = loginService.authenticate(id, password);

        /*
         * v [Step 4-1-03] 가져온 account 객체가 null 아닌 경우
         * request.getSession() 메소드를 통해 Session을 구해 UserAccount 이름으로 Session 에
         * Attribute로 저장한다. 그리고 "redirect:/loginSuccess.do" 값을 반환한다. null 인경우
         * "login" 반환한다.
         */
        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("UserAccount", account);
            return "redirect:/loginSuccess.do";
        }

    	return "login";
    }

    // v [Step 4-1-04] loginSuccess.do 로 호출된 처리
    // (@RequestMapping(value="/loginSuccess.do", method=RequestMethod.GET) 를 위한
    // 메소드(loginSuccess) 를 void 타입의 public 메소드를 만들어라.
    @RequestMapping(value = "/loginSuccess.do", method = RequestMethod.GET)
    public void loginSuccess() {
        log.info("+ loginSuccess()");
    }

    @RequestMapping("/exception.do")
    public String invokeException(@RequestParam("id") String id ) throws Exception {

        sampleService.invokeMethodAException();

        return null;
    }

}
