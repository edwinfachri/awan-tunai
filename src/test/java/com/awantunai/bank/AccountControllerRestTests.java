// package com.awantunai.bank;
//
// import static org.hamcrest.Matchers.*;
// import static org.junit.Assert.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//
// import com.awantunai.bank.repository.UserRepository;
// import com.awantunai.bank.repository.AccountRepository;
// import com.awantunai.bank.repository.AdminRepository;
// import com.awantunai.bank.model.User;
// import com.awantunai.bank.model.Account;
// import com.awantunai.bank.model.Admin;
//
// import java.io.IOException;
// import java.nio.charset.Charset;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.text.SimpleDateFormat;
// import java.util.Date;
//
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import org.springframework.mock.http.MockHttpOutputMessage;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.context.WebApplicationContext;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = BankApplication.class)
// @WebAppConfiguration
// public class AccountControllerRestTests {
//
//   private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//             MediaType.APPLICATION_JSON.getSubtype(),
//             Charset.forName("utf8"));
//
//   private HttpMessageConverter mappingJackson2HttpMessageConverter;
//
//   private String sessionTest = "session_test";
//
//   private User user;
//
//   private Admin admin;
//
//     private Account account;
//
//   private List<Account> accountList = new ArrayList<>();
//
//   private MockMvc mockMvc;
//
//   @Autowired
//   private AccountRepository accountRepository;
//
//   @Autowired
//   private UserRepository userRepository;
//
//   @Autowired
//   private AdminRepository adminRepository;
//
//   @Autowired
//   private WebApplicationContext webApplicationContext;
//
//   @Before
//   public void setup() throws Exception {
//     SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
// 		Date date=formatter.parse("19940917");
//     this.mockMvc = webAppContextSetup(webApplicationContext).build();
//     this.accountRepository.deleteAllInBatch();
//     this.adminRepository.deleteAllInBatch();
//     this.userRepository.deleteAllInBatch();
//     this.admin = adminRepository.save(new Admin("username_test", "password_test", Long.valueOf(1), sessionTest, 1));
//     this.account = accountRepository.save(new Account(user, "1233211233", "123321", 5000));
//     this.user = userRepository.save(new User("Edwin", "Wicaksono", date, "123321", "Homee"));
//     this.accountList.add(account);
//   }
//
// 	@Test
// 	public void accessWithoutSessionId() throws Exception {
//     // Fetch accounts without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts")).andExpect(status().isBadRequest());
//     // Create account without sessionId Parameter
//     this.mockMvc.perform(post("/awantunai/accounts")).andExpect(status().isBadRequest());
//     // Get account without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts/1")).andExpect(status().isBadRequest());
//     // Deposit money to account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/deposit")).andExpect(status().isBadRequest());
//     // Withdraw money from account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/withdraw")).andExpect(status().isBadRequest());
//     // Fetch account without sessionId Parameter
//     this.mockMvc.perform(delete("/awantunai/accounts/1")).andExpect(status().isBadRequest());
// 	}
//
//   @Test
//   public void accessWithoutLogin() throws Exception {
//     // Fetch accounts without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts?sessionId=false"))
//       .andExpect(status().isBadRequest())
//       .andExpect(content().string(containsString("Please login first.")));
//     // Create account without sessionId Parameter
//     this.mockMvc.perform(post("/awantunai/accounts?sessionId=false"))
//       .andExpect(status().isBadRequest());
//     // Get account without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts/1?sessionId=false"))
//       .andExpect(status().isBadRequest())
//       .andExpect(content().string(containsString("Please login first.")));
//     // Deposit money to account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/deposit?sessionId=false"))
//       .andExpect(status().isBadRequest());
//     // Withdraw money from account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/withdraw?sessionId=false"))
//       .andExpect(status().isBadRequest());
//     // Fetch account without sessionId Parameter
//     this.mockMvc.perform(delete("/awantunai/accounts/1?sessionId=false"))
//       .andExpect(status().isBadRequest())
//       .andExpect(content().string(containsString("Please login first.")));
//   }
//
//   @Test
//   public void accessWithLoginWithoutData() throws Exception {
//     // Fetch accounts without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts?sessionId="+sessionTest))
//       .andExpect(status().isOk());
//     // Create account without sessionId Parameter
//     this.mockMvc.perform(post("/awantunai/accounts?sessionId="+sessionTest))
//       .andExpect(status().isBadRequest());
//     // Get account without sessionId Parameter
//     this.mockMvc.perform(get("/awantunai/accounts/"+account.getId()+"?sessionId="+sessionTest))
//       .andExpect(status().isOk());
//     // Deposit money to account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/deposit?sessionId="+sessionTest))
//       .andExpect(status().isBadRequest());
//     // Withdraw money from account without sessionId Parameter
//     this.mockMvc.perform(put("/awantunai/accounts/withdraw?sessionId="+sessionTest))
//       .andExpect(status().isBadRequest());
//     // Fetch account without sessionId Parameter
//     this.mockMvc.perform(delete("/awantunai/accounts/"+account.getId()+"?sessionId="+sessionTest))
//       .andExpect(status().isOk());
//   }
//
//
// }
