package com.example.servicioevaluaciones.controller;

import com.example.servicioevaluaciones.entity.Docente;
import com.example.servicioevaluaciones.entity.Evaluacion;
import com.example.servicioevaluaciones.entity.ProyectoTesis;
import com.example.servicioevaluaciones.repository.DocenteRepository;
import com.example.servicioevaluaciones.repository.ProyectoTesisRepository;
import com.example.servicioevaluaciones.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private ProyectoTesisRepository proyectoTesisRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @PostMapping("/registrar")
    public Evaluacion registrarEvaluacion(
            @RequestParam Long proyectoTesisId,
            @RequestParam Long asesorId,
            @RequestParam String observaciones,
            @RequestParam String recomendaciones,
            @RequestParam Evaluacion.EstadoEvaluacion estado) {
        ProyectoTesis proyectoTesis = proyectoTesisRepository.findById(proyectoTesisId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto de tesis no encontrado"));

        Docente asesor = docenteRepository.findById(asesorId)
                .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));


        return evaluacionService.registrarEvaluacion(proyectoTesisId, asesorId, observaciones, recomendaciones, estado);
    }

    @GetMapping("/proyectos/{proyectoTesisId}/evaluaciones")
    public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorProyecto(@PathVariable Long proyectoTesisId) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorProyecto(proyectoTesisId);
        return ResponseEntity.ok(evaluaciones);
    }
}
