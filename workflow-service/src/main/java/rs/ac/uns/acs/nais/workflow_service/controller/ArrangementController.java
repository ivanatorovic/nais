package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.ArrangementDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IArrangementService;

import java.util.List;

@RestController
@RequestMapping("/api/arrangements")
public class ArrangementController {

    private final IArrangementService arrangementService;

    public ArrangementController(IArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @GetMapping
    public List<ArrangementDTO> getAllArrangements() {
        return arrangementService.getAllArrangements();
    }

    @GetMapping("/{id}")
    public ArrangementDTO getArrangementById(@PathVariable Long id) {
        return arrangementService.getArrangementById(id);
    }

    @PostMapping
    public ArrangementDTO createArrangement(@RequestBody ArrangementDTO arrangementDTO) {
        return arrangementService.createArrangement(arrangementDTO);
    }

    @PatchMapping("/{id}")
    public ArrangementDTO updateArrangement(@PathVariable Long id, @RequestBody ArrangementDTO arrangementDTO) {
        return arrangementService.updateArrangement(id, arrangementDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteArrangement(@PathVariable Long id) {
        arrangementService.deleteArrangement(id);
    }

    @PostMapping("/{arrangementId}/based-on/{workflowId}")
    public ArrangementDTO createBasedOnRelationship(@PathVariable Long arrangementId,
                                                    @PathVariable Long workflowId) {
        return arrangementService.createBasedOnRelationship(arrangementId, workflowId);
    }

    @DeleteMapping("/{arrangementId}/based-on")
    public void deleteBasedOnRelationship(@PathVariable Long arrangementId) {
        arrangementService.deleteBasedOnRelationship(arrangementId);
    }

    @GetMapping("/{arrangementId}/based-on")
    public WorkflowDTO getWorkflowForArrangement(@PathVariable Long arrangementId) {
        return arrangementService.getWorkflowForArrangement(arrangementId);
    }

    @PostMapping("/{arrangementId}/offers/{offerId}")
    public ArrangementDTO addOfferToArrangement(@PathVariable Long arrangementId,
                                                @PathVariable Long offerId) {
        return arrangementService.addOfferToArrangement(arrangementId, offerId);
    }

    @DeleteMapping("/{arrangementId}/offers/{offerId}")
    public void deleteOfferFromArrangement(@PathVariable Long arrangementId,
                                           @PathVariable Long offerId) {
        arrangementService.deleteOfferFromArrangement(arrangementId, offerId);
    }

    @GetMapping("/{arrangementId}/offers")
    public List<OfferDTO> getOffersForArrangement(@PathVariable Long arrangementId) {
        return arrangementService.getOffersForArrangement(arrangementId);
    }
}