![Copy of Copy of architecture](https://github.com/user-attachments/assets/1739c98d-1b62-4548-a7ad-ae3d704ac219)
ERD 설계 이유
1. User 테이블
userId: 사용자를 구분하는 고유한 값입니다.
userName: 사용자의 이름을 저장합니다.
email: 사용자의 이메일을 저장합니다.
이유: 특강을 신청할 때, 사용자를 식별하고 관리하기 위해 사용자 정보를 저장하는 테이블이 필요합니다.

2. SpecialLecture 테이블
lectureId: 특강을 구분하는 고유한 값입니다.
lectureName: 특강의 제목을 저장합니다.
lectureDate: 특강이 열리는 날짜를 저장합니다.
lecturer: 특강을 진행하는 강사의 이름을 저장합니다.
이유: 특강에 대한 정보를 저장하여 사용자가 어떤 특강을 신청했는지 관리하기 위해 필요한 테이블입니다.

3. LectureRegistration 테이블
userId: 특강을 신청한 사용자의 ID입니다.
lectureId: 신청한 특강의 ID입니다.
registrationDate: 신청한 날짜를 저장합니다.
이유: 여러 사용자가 같은 특강을 신청할 수 있고, 한 사용자가 여러 특강을 신청할 수 있기 때문에, 사용자와 특강 간의 관계를 관리하기 위한 테이블입니다. 이 테이블을 통해 중복 신청을 방지하고, 신청 인원을 관리할 수 있습니다.
