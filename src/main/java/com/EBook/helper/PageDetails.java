//package com.EBook.helper;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//import org.springframework.data.domain.Page;
//
//
//
//public class PageDetails {
//    public static <U,V>PageableResponse<U> getPageableResponse(Page<U> page,Class<V> type){
//        List<U> entity = page.getContent();
//        PageableResponse<V> pageableResponse = new PageableResponse<>();
//        
//        List<V> list=new ArrayList<>();
//        for(U l:entity) {
//        	list.add((V) l);
//        }
////        List<V> userDtoList = entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());
//        
//        pageableResponse.setContent(list);
//        pageableResponse.setPageNumber(page.getNumber()+1);
//        pageableResponse.setPageSize(page.getSize());
//        pageableResponse.setTotalPages(page.getTotalPages());
//        pageableResponse.setTotalElements(page.getTotalElements());
//        pageableResponse.setLastPage(page.isLast());
//
//        return pageableResponse;
//    }
//}
