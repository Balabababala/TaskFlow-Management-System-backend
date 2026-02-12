//package com.example.demo.integration;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import com.example.demo.model.dto.WorkflowDto;
//import com.example.demo.model.entity.Role;
//import com.example.demo.model.entity.User;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.secure.CustomUserDetails;
//import com.example.demo.service.WorkflowService;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional  // 每次測試後會自動 rollback，不影響資料庫
//public class WorkflowServiceIntegrationTest {
//
//    @Autowired
//    private WorkflowService workflowService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    void testCreateAndFindAllWorkflow() {
//
//        // 1️⃣ 建立 Role
//        Role role = new Role();
//        role.setRoleName("TEST_ROLE");
//        role.setActive(true);
//        role = roleRepository.save(role);  // 保存並取得自動生成的 ID
//
//        // 2️⃣ 建立 User
//        User user = new User();
//        user.setUsername("testuser");
//        user.setEmail("user@test.com");
//        user.setPassword("123");
//        user.setActive(true);
//        user.setRole(role);
//        user = userRepository.save(user);  // 保存並取得 ID
//
//        // 3️⃣ 建立 WorkflowDto
//        WorkflowDto dto = new WorkflowDto();
//        dto.setName("Test Workflow");
//        dto.setVersion(1);
//        dto.setCreatedAt(LocalDateTime.now());
//        dto.setCreatedBy(user.getId());  // 用剛存的 User ID
//
//        // 4️⃣ 建立 Workflow
//        CustomUserDetails userDetails = new CustomUserDetails(user);
//        workflowService.createWorkflow(userDetails,dto);
//
//        // 5️⃣ 取得所有 Workflow
//        List<WorkflowDto> allWorkflows = workflowService.findAllWorkflow();
//
//        // 6️⃣ 驗證
//        assertFalse(allWorkflows.isEmpty(), "Workflow list should not be empty");
//        assertTrue(allWorkflows.stream()
//                .anyMatch(w -> "Test Workflow".equals(w.getName())), "Should contain the created workflow");
//
//        // 7️⃣ 輸出查看
//        allWorkflows.forEach(System.out::println);
//    }
//}
