CREATE DATABASE IF NOT EXISTS easycompany default character set utf8;

grant all privileges on *.* to 'user1'@'%';

use easycompany;

DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    EMPLOYEEID    VARCHAR(20)  NOT NULL,
    NAME          VARCHAR(100) NOT NULL,
    AGE           INTEGER,
    DEPARTMENTID  VARCHAR(10)  NOT NULL,
    PASSWORD      VARCHAR(100) NOT NULL,
    EMAIL         VARCHAR(200)
);
ALTER TABLE employee ADD PRIMARY KEY (EMPLOYEEID);

DROP TABLE IF EXISTS department;
CREATE TABLE department
(
    DEPTID          VARCHAR(10) NOT NULL,
    DEPTNAME        VARCHAR(100) NOT NULL,
    SUPERDEPTID     VARCHAR(10) NOT NULL,
    DEPTH           INTEGER,
    DESCRIPTION     VARCHAR(512)
);
ALTER TABLE department ADD PRIMARY KEY (DEPTID);

-- index

-- init data
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('1','Kim GilDong',28,'1200','1','kkd@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('10','어시구',44,'1200','10','elvis@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('11','황달수',55,'1200','11','James@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('12','박영업',73,'2100','12','sales@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('13','노가리',59,'3200','13','foxylady@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('14','이기자',20,'4100','14','expensive@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('15','이마애',15,'5100','15','soexpensive@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('16','제임수',28,'5300','16','james@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('17','황커플',34,'1400','17','hwang@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('18','Kim NaRi',37,'1400','18','godfather@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('19','박사랑',60,'1400','19','lovelove@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('2','Kim GilSu',39,'1100','2','kks@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('20','용파리',60,'4100','20','king@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('21','맹주사',66,'2200','21','maeng@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('22','이만세',44,'5100','22','lovelymouse@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('23','허허실',27,'5200','23','master@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('24','마장수',55,'4100','24','flame@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('25','아리요',28,'4100','25','srisri@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('26','홍길동',33,'4200','26','hkd@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('27','박박네',25,'2300','27','ppn@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('28','반짝이',28,'2400','28','soul@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('29','우기자',31,'2100','29','mamama@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('3','강감찬',17,'1200','3','kkc@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('30','남남이',25,'3100','30','namnam@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('4','Kim Angel',56,'3300','4','uri@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('5','박찬후',36,'2400','5','ppc@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('6','재기찬',34,'1300','6','jackie@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('7','호호만',18,'5200','7','holly@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('8','봉다리',33,'5200','8','2u@easycompany.com');
INSERT INTO employee (EMPLOYEEID, NAME, AGE, DEPARTMENTID, PASSWORD, EMAIL) VALUES('9','기마긴',60,'4200','9','kmk@easycompany.com');

INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('1000','경영기획실','1000',1,'');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('1100','회식메뉴혁신팀','1000',2,'매번 삼겹살 지겹지 않으세요? 저희 회식메뉴혁신팀에서는 지속적인 즐거운 회사문화 조성을 위해...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('1200','점심메뉴기획팀','1000',2,'점심시간마다 반복되는 오늘은 뭘먹을까? 고민은 점심시간의 즐거움을 저하시키며 점심메뉴 추천자에게 심한 스트레스를 유발 시킬수 있습니다. 저희 점심메뉴기획팀은 날씨,뉴스등 주변여건을 고려하여 가장 베스트 점심메뉴를 추천해 드리는 최신식 점심메뉴프로그램을 도입하여...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('1300','야근금지역량팀','1000',2,'오후 6시 이후엔 소등 및 사원들의 의자에 고압전류를...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('1400','사랑의짝대기팀','1000',2,'늘어가는 솔로 사원들을 위한 회사의 고민이 많습니다. 저의 사짝팀은 회사내 솔로 보틀넥현상을 근본적으로 해결하고자 옆 건물 fun company와 소개팅을 지속적으로...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('2000','경영지원실','2000',1,'');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('2100','재무팀','2000',2,'저희 재무팀은 빚/보증/채무변제/사채와는 아무런 관계가 없습니다.');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('2200','친절한인사팀','2000',2,'안녕하세요. 채용/승진/조직관련 업무를 담당하는 친절한 인사팀입니다. 저희 인사팀은 친절에 사활을...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('2300','사원복지만땅팀','2000',2,'일은 열심히 하는데 회사가 챙겨주는건 이것 저것 부족한게 많으시죠? 예산이 부족하면 몸으로 때워 드립니다. 연락주세요.');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('2400','사내커플관리팀','2000',2,'사내 커플이란 이유로 차별을 받으시나요? 저희에게 연락 주시면 즉각 응징..?');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('3000','IT연구소','3000',1,'');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('3200','노가다방지 도구개발팀','3000',2,'반복적인 단순 업무 지겹지 않으세요? 손건강을 위해서 저희 도구를 이용해 보세요. 손이 행복해집니다.');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('3300','9to5솔루션개발팀','3000',2,'정시에 출근해서 한시간 일찍 퇴근하자. 이게 말이 되냐구요? 저희 솔루션을 사용해 보세요.');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('4000','공공사업부','4000',1,'');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('4200','도로교통시스템팀','4000',2,'안녕하세요. 도로교통시스템팀입니다.');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('4300','연말정산이익증대팀','4000',2,'연말정산하면 마이너스. 이거 왜이럴까요?');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('5000','금융사업부','5000',1,'');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('5100','카드수수료할인팀','5000',2,'카드수수료를 절약하는 금융어플리케이션이 필요하세요? 저희가 도와 드리겠습니다. 아니, 고객님은 카드수수료를 올리는게 필요하시다구요? 그건...');
INSERT INTO department (DEPTID, DEPTNAME, SUPERDEPTID, DEPTH, DESCRIPTION) VALUES('5200','증권대박팀','5000',2,'정확한 증권 시세 분석을 위한 첨단 시스템을 제공합니다. 어떻게 시스템이 분석할수 있냐구요? 음 ...');
