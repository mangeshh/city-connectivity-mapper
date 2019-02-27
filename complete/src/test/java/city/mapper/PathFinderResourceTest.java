package city.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mc.main.Application;
import com.mc.resource.PathFinderResource;

@RunWith(SpringRunner.class)
@WebMvcTest(PathFinderResource.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:application.test.properties")
public class PathFinderResourceTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testIsConnectedCall1() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=Boston&destination=Newark").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("yes", result.getResponse().getContentAsString());
	}
	
	
	@Test
	public void testIsConnectedCallV32() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connectedV3?origin=L&destination=M").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("yes", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testIsConnectedCallV33() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connectedV3?origin=L&destination=M1").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("no", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testIsConnectedCall2() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=L&destination=M").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("yes", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testIsConnectedCall3() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=L&destination=M1").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("no", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testIsConnectedBadReqCall() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connectedV3?origin=L").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
 	}
	
	@Test
	public void testIsConnectedBadReqCall1() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin1=L&destination=M1").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(400, result.getResponse().getStatus());
 	}
	
	@Test
	public void testIsConnectedBaseCall1() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=Boston&destination=Newark").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("yes", result.getResponse().getContentAsString());
 	}
	
	@Test
	public void testIsConnectedBaseCall2() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=Boston&destination=Philadelphia").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("yes", result.getResponse().getContentAsString());
 	}
	
	@Test
	public void testIsConnectedBaseCall3() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/connected?origin=Philadelphia&destination=Albany").accept(MediaType.ALL);
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("no", result.getResponse().getContentAsString());
 	}
	
}
