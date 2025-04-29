package job.data.domain.user.domain.admin;

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
// 정부용
public class Admin {
    @Id
    String adminId;

    String loginId;

    String password;

    @Transient
    private String passwordCheck;
}
