package rs.ac.uns.acs.nais.workflow_service.dto;

public class WorkflowOfferStatsDTO {
    private Long workflowId;
    private String workflowName;
    private Long brojAranzmana;
    private Long ukupanBrojPonuda;
    private Double prosecnoPonudaPoAranzmanu;

    public WorkflowOfferStatsDTO(Long workflowId,
                                 String workflowName,
                                 Long brojAranzmana,
                                 Long ukupanBrojPonuda,
                                 Double prosecnoPonudaPoAranzmanu) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.brojAranzmana = brojAranzmana;
        this.ukupanBrojPonuda = ukupanBrojPonuda;
        this.prosecnoPonudaPoAranzmanu = prosecnoPonudaPoAranzmanu;
    }

    public Long getWorkflowId() { return workflowId; }
    public String getWorkflowName() { return workflowName; }
    public Long getBrojAranzmana() { return brojAranzmana; }
    public Long getUkupanBrojPonuda() { return ukupanBrojPonuda; }
    public Double getProsecnoPonudaPoAranzmanu() { return prosecnoPonudaPoAranzmanu; }
}

