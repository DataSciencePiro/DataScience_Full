package job.data.domain.user.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
// 기업용
public class User {

    @Id
    private String userId;

    private String loginId;

    private String password;

    private String businessNumber;

    // 기업용, 관리자용, 마스터용
    private String type;

    // 기업용은 무조건 false, 마스터가 관리자 지명해야됨
    private boolean authority;

    @Transient
    private String passwordCheck;

    @Transient
    private String errorMessage;

}
