package springbootmonolithic.domain.member.command.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springbootmonolithic.common.domain.member.role.RoleType;
import springbootmonolithic.domain.member.command.application.dto.request.SignUpRequestDTO;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.MemberRole;
import springbootmonolithic.domain.member.command.domain.repository.MemberRepository;
import springbootmonolithic.domain.member.command.domain.repository.MemberRoleRepository;
import springbootmonolithic.domain.member.query.service.MemberQueryService;
import springbootmonolithic.exception.InvalidMemberException;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberQueryService memberQueryService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(
            MemberRepository memberRepository,
            MemberRoleRepository memberRoleRepository,
            MemberQueryService memberQueryService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.memberQueryService = memberQueryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(SignUpRequestDTO signupRequestDTO) {
        memberQueryService.validateEmail(signupRequestDTO.getEmail());
        memberQueryService.checkNicknameDuplicated(signupRequestDTO.getNickname());

        Member member = memberRepository.save(
                Member.createMember(
                        signupRequestDTO.getEmail(),
                        passwordEncoder.encode(signupRequestDTO.getPassword()),
                        signupRequestDTO.getNickname(),
                        signupRequestDTO.getImage()
                )
        );

        memberRoleRepository.save(
                MemberRole.builder()
                        .memberCode(member.getCode())
                        .roleCode(RoleType.USER.getOrder())
                        .build()
        );
    }

    @Override
    public void deactivateMemberBy(int memberCode) {
        memberRepository.findByCodeAndActiveTrue(memberCode)
                .orElseThrow(() -> new InvalidMemberException("유효하지 않은 회원입니다."))
                .deactivate();
    }
}
