package com.hongying.elastictest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongying.beans.Employee;
import com.hongying.dao.EmployeeRepository;

@RestController
@RequestMapping("/elastictest")
public class ElasticSearchTest {
	private Logger log=LoggerFactory.getLogger(ElasticSearchTest.class);
	@Autowired
	private EmployeeRepository repository;
	@GetMapping("/add")
	public Employee AddEmployee(String id,Integer age,String name,String about){
		if(id==null){
			id="1000";
		}
		if(name==null){
			name="jake"+id;
		}
		if(age==null){
			age=1000;
		}
		Employee em=new Employee();
		em.setId(id);
		em.setAge(age);
		em.setFirstName(name);
		em.setAbout(about);
		Employee yee=repository.save(em);
		return yee;
	}
	@GetMapping("/getes")
	public List<Employee> getLst(int pnum,String query){
		//es 搜索默认第一页页码是0
		//拼接查询条件
		FunctionScoreQueryBuilder funcitonScoreBuilder=QueryBuilders.functionScoreQuery()
				//.add(QueryBuilders.queryStringQuery("firstName:"+query), ScoreFunctionBuilders.weightFactorFunction(100))
				.add(QueryBuilders.matchPhraseQuery("firstName",query), ScoreFunctionBuilders.weightFactorFunction(100))
				.add(QueryBuilders.matchPhraseQuery("about", query), ScoreFunctionBuilders.weightFactorFunction(100))
				//设置权重分   求和模式
				.scoreMode("sum")
				//设置权重最低分
				.setMinScore(10);
		//设置分页
		Pageable pageable=new PageRequest(pnum, 10);
		//按照分页查询
		SearchQuery searchQuery=new NativeSearchQueryBuilder()
				.withPageable(pageable)
				.withQuery(funcitonScoreBuilder)
				.build();
		Page<Employee> page=repository.search(searchQuery);
		return page.getContent();
		
	}
	
	
	@GetMapping("/getinfo")
	public String GetInfo(HttpServletRequest request){
		String remoteIp=request.getRemoteAddr();//前面有负载均衡就会获取负载均衡器的IP
		String realIp = request.getHeader("X-Real_IP");//Nginx设置请求头的传递Ip
		String forwardIp = request.getHeader("X-Forwarded-For");
		String ss=String.format("remoteIp:%s,realIp:%s,forwardIp:%s", remoteIp,realIp,forwardIp);
		System.out.println(ss);
		return ss;
	}
}
