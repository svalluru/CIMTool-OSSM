// camel-k: language=java dependency=camel:sql
// camel-k: dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0.redhat-00001
// camel-k: dependency=mvn:org.postgresql:postgresql:9.4.1212
import org.apache.camel.Exchange;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBController extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		registerDatasource();

		rest()
		.get("/checkcreds?token={token}")
		.produces("text/plain")
		.to("direct:checkcreds") ;

		from("direct:checkcreds")
		.log("select * from credentials where token='${header.token}'")
		.setBody(simple("select * from credentials where token='${header.token}';"))
		.to("jdbc:myPostgresDS")
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				ArrayList<HashMap> colvals = exchange.getIn().getBody(ArrayList.class);
				
				if(colvals.get(0).get("accessallowed").equals("true"))
				{
					String str = "{\"accesskey\":\"mytest\",\"secretkey\":\"accessallowed\",\"instanceids\":\"sss\"}";
					exchange.getIn().setBody(str);
				}else{
					exchange.getIn().setBody(colvals.get(0).get("accessallowed"));
				}
			}
		});

	}

	private void registerDatasource() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		ds.setUsername("admin");
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setPassword("admin");
		ds.setUrl("jdbc:postgresql://postgres.db:5432/sampledb");
	 
		this.getContext().getRegistry().bind("myPostgresDS", ds);
	  }
}
