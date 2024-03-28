package yaroslav.redickh_idf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yaroslav.redickh_idf.entity.MonthlyLimit;
import yaroslav.redickh_idf.service.MonthlyLimitService;

@RestController
@RequestMapping("/limit")
public class MonthlyLimitController {

    private final MonthlyLimitService limitService;
    @Autowired
    public MonthlyLimitController( MonthlyLimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping
    private MonthlyLimit createMonthLimit(@RequestBody MonthlyLimit limit){
        return limitService.saveMonthLimit(limit);
    }
    @PostMapping("/change")
    private MonthlyLimit changeLimit(@RequestParam Long userId, @RequestParam Double newLimit){
        return limitService.changeLimit(userId, newLimit);
    }


}
