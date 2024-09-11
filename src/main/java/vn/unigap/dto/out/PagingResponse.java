package vn.unigap.dto.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.unigap.entities.Employer;
@Getter
@Setter
@NoArgsConstructor
public class PagingResponse {
    public Integer page;
    public  Integer pageSize;
    public Long totalElements;
    public Long totalPages;
    public Iterable<Employer> data;
    public PagingResponse(Integer page,Integer pageSize, Long totalElements,Iterable<Employer> data){
        setData(data);
        setPage(page);
        setPageSize(pageSize);
        double a = (double)totalElements/(double) pageSize;
        long totalPages= (long) Math.ceil(a);
        setTotalPages(totalPages);
        setTotalElements(totalElements);
    }
}
