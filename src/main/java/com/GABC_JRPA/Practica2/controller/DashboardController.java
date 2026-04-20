package com.GABC_JRPA.Practica2.controller;

import com.GABC_JRPA.Practica2.model.Curso;
import com.GABC_JRPA.Practica2.model.Reserva;
import com.GABC_JRPA.Practica2.model.Calificacion;
import com.GABC_JRPA.Practica2.model.Usuario;
import com.GABC_JRPA.Practica2.repository.ClienteRepository;
import com.GABC_JRPA.Practica2.repository.CursoRepository;
import com.GABC_JRPA.Practica2.repository.EstudianteRepository;
import com.GABC_JRPA.Practica2.repository.ReservaRepository;
import com.GABC_JRPA.Practica2.repository.CalificacionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;
    private final CalificacionRepository calificacionRepository; // Nuevo

    public DashboardController(EstudianteRepository estRepo, CursoRepository curRepo,
                               ClienteRepository cliRepo, ReservaRepository resRepo,
                               CalificacionRepository calRepo) {
        this.estudianteRepository = estRepo;
        this.cursoRepository = curRepo;
        this.clienteRepository = cliRepo;
        this.reservaRepository = resRepo;
        this.calificacionRepository = calRepo;
    }

    @GetMapping
    public String verDashboard(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        model.addAttribute("nombreUsuario", usuario.getNombre());

        // 1. ESTUDIANTES
        model.addAttribute("totalEstudiantes", estudianteRepository.count());
        model.addAttribute("estSistemas", estudianteRepository.countByCarrera("Sistemas Computacionales"));
        model.addAttribute("estMecatronica", estudianteRepository.countByCarrera("Mecatrónica"));
        model.addAttribute("estAmbiental", estudianteRepository.countByCarrera("Ambiental"));
        model.addAttribute("estDatos", estudianteRepository.countByCarrera("Ciencias de Datos"));

        // 2. CURSOS
        Iterable<Curso> cursos = cursoRepository.findAll();
        List<String> nombresCursos = new ArrayList<>();
        List<Integer> creditosCursos = new ArrayList<>();
        for (Curso c : cursos) {
            nombresCursos.add(c.getNombre());
            creditosCursos.add(c.getCreditos());
        }
        if(nombresCursos.isEmpty()) { nombresCursos.add("Sin Cursos"); creditosCursos.add(0); }
        model.addAttribute("totalCursos", cursoRepository.count());
        model.addAttribute("nombresCursos", nombresCursos);
        model.addAttribute("creditosCursos", creditosCursos);

        // 3. CLIENTES
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("clientesConCorreo", clienteRepository.countConCorreo());
        model.addAttribute("clientesSinCorreo", clienteRepository.countSinCorreo());

        // 4. RESERVAS
        Iterable<Reserva> reservas = reservaRepository.findAll();
        Map<Integer, Integer> reservasPorHabitacion = new HashMap<>();
        long totalReservas = 0;
        for (Reserva r : reservas) {
            int hab = r.getHabitacion();
            reservasPorHabitacion.put(hab, reservasPorHabitacion.getOrDefault(hab, 0) + 1);
            totalReservas++;
        }
        List<String> labelsReservas = new ArrayList<>();
        List<Integer> dataReservas = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : reservasPorHabitacion.entrySet()) {
            labelsReservas.add("Hab " + entry.getKey());
            dataReservas.add(entry.getValue());
        }
        if(labelsReservas.isEmpty()) { labelsReservas.add("Sin reservas"); dataReservas.add(0); }
        model.addAttribute("totalReservas", totalReservas);
        model.addAttribute("labelsReservas", labelsReservas);
        model.addAttribute("dataReservas", dataReservas);

        // 5. CALIFICACIONES (Nuevo)
        Iterable<Calificacion> calificaciones = calificacionRepository.findAll();
        long totalCalificaciones = 0;
        int aprobados = 0;
        int reprobados = 0;
        for(Calificacion cal : calificaciones){
            totalCalificaciones++;
            if(cal.getCalificacion() >= 6.0) aprobados++;
            else reprobados++;
        }
        model.addAttribute("totalCalificaciones", totalCalificaciones);
        model.addAttribute("alumnosAprobados", aprobados);
        model.addAttribute("alumnosReprobados", reprobados);

        return "dashboard";
    }
}