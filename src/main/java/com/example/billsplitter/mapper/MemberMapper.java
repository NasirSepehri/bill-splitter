package com.example.billsplitter.mapper;

import com.example.billsplitter.dto.event.MemberDto;
import com.example.billsplitter.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface MemberMapper {

    MemberDto toDto(Member member);

    Member toEntity(MemberDto memberDto);

}
