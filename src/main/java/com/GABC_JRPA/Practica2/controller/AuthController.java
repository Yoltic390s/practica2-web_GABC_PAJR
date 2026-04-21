package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @GetMapping({"/", "/login"})
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        Usuario usuarioAutenticado = usuarioRepository.findByBoletaAndContrasena(usuario.getBoleta(), usuario.getContrasena());

        if (usuarioAutenticado != null) {
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Boleta o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        // AQUÍ DESTRUIMOS LA SESIÓN PARA SALIR
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String mostrarFormularioRecuperar() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String procesarRecuperar(@RequestParam("boleta") String boleta,
                                    @RequestParam("nuevaContrasena") String nuevaContrasena,
                                    Model model) {
        Usuario usuarioExistente = usuarioRepository.findByBoleta(boleta);
        if (usuarioExistente != null) {
            usuarioExistente.setContrasena(nuevaContrasena);
            usuarioRepository.save(usuarioExistente);
            return "redirect:/login";
        } else {
            model.addAttribute("error", "No se encontró ningún usuario con esa boleta.");
            return "forgot-password";
        }
    }
}