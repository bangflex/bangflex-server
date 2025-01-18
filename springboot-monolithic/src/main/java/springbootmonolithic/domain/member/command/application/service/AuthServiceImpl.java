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
import springbootmonolithic.domain.member.query.service.AuthQueryService;
import springbootmonolithic.domain.member.query.service.MemberQueryService;

@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberQueryService memberQueryService;
    private final AuthQueryService authQueryService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            MemberRepository memberRepository,
            MemberRoleRepository memberRoleRepository,
            MemberQueryService memberQueryService,
            AuthQueryService authQueryService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.memberQueryService = memberQueryService;
        this.authQueryService = authQueryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(SignUpRequestDTO signupRequestDTO) {
        authQueryService.validateEmail(signupRequestDTO.getEmail());
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
}
