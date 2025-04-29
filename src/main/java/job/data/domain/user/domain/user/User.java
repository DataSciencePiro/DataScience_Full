package job.data.domain.user.domain.user;


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
    private     String userId;

    private     String loginId;

    private String password;

    private String businessNumber;

    @Transient
    private String passwordCheck;

    @Transient
    private String errorMessage;

}
