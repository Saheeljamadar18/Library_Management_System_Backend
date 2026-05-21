package saheel_library_management.project.Library_Management_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saheel_library_management.project.Library_Management_System.Service.DashboardService;
import saheel_library_management.project.Library_Management_System.response.DashboardStatsDto;

@RestController
@RequestMapping("/dashboard/apis")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsDto getStats() {
        return dashboardService.getStats();
    }
}
