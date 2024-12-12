package uv.tcs.dognload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import uv.tcs.dognload.repositories.UserRepo;
import uv.tcs.dognload.model.User;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public List<User> listarTodosLosUsuarios () {
        return userRepo.findAll();
    }
}
