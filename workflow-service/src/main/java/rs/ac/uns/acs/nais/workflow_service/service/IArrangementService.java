package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.*;

import java.util.List;

public interface IArrangementService {

    List<ArrangementDTO> getAllArrangements();

    ArrangementDTO getArrangementById(Long id);

    ArrangementDTO createArrangement(ArrangementDTO arrangementDTO);

    ArrangementDTO updateArrangement(Long id, ArrangementDTO arrangementDTO);

    void deleteArrangement(Long id);

    ArrangementDTO createBasedOnRelationship(Long arrangementId, Long workflowId);

    void deleteBasedOnRelationship(Long arrangementId);

    WorkflowDTO getWorkflowForArrangement(Long arrangementId);

    ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId);

    void deleteOfferFromArrangement(Long arrangementId, Long offerId);

    List<OfferDTO> getOffersForArrangement(Long arrangementId);

    List<ArrangementPriceDTO> getArrangementPriceAnalysis();

    List<ArrangementRatingDTO> getArrangementRatings();


}