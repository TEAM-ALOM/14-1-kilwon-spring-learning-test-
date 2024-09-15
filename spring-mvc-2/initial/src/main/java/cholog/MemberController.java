package cholog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    //Member정보를 담을 리스트 생성
    private final List<Member> members = new ArrayList<>();
    //index를 관리할 수 있는 AtomicLong을 index로 사용 (id로 줌)
    private final AtomicLong index = new AtomicLong(1);

    //POST 요청으로 온 /members를 처리하게 함
    @PostMapping("/members")
    //@RequestBody로 본문에 있는 Member의 내용을 받음
    public ResponseEntity<Void> create(@RequestBody Member member) {
        // TODO: member 정보를 받아서 생성한다.
        //받은 member의 정보를 이용해서 Member Class의 toEntity 메서드로 새로운 멤버를 제작
        Member newMember = Member.toEntity(member, index.getAndIncrement());
        //멤버 목록(저장소)에 추가
        members.add(newMember);
        //성공적으로 추가되었는지 결과 url, 201 Created 상태 반환
        return ResponseEntity.created(URI.create("/members/" + newMember.getId())).build();
    }

    //GET 요청으로 온 /members 처리하게 함
    @GetMapping("/members")

    public ResponseEntity<List<Member>> read() {
        // TODO: 저장된 모든 member 정보를 반환한다.
        //HTTP 200 응답 회원 리스트를 json으로 반환
        return ResponseEntity.ok().body(members);
    }

    //PUT 요청으로 온 /members와 id를 같이 전달받아 처리
    @PutMapping("/members/{id}")
    //@PathVarialbe 어노테이션을 이용하여 url에 있는 id를 받아옴, 본문에 았는 수정될 Member 정볼르 받아옴
    public ResponseEntity<Void> update(@RequestBody Member newMember, @PathVariable Long id) {
        // TODO: member의 수정 정보와 url 상의 id 정보를 받아 member 정보를 수정한다.
        //Member리스트를 stream으로 받은 후, filter를 이용하여 id가 일치하는 값을 찾음
        //그리고 가장 앞의 값을 반환
        Member member = members.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);
        //해당 값을 update 메서드로 새로운 멤버로 업데이트
        member.update(newMember);
        //HTTP 응답 200ok 반환
        return ResponseEntity.ok().build();
    }

    //Delete요청으로 온 members url과 id를 받음
    @DeleteMapping("/members/{id}")
    //@PathVariable 어노테이션으로 id를 찾아 받음
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 member를 삭제한다.
        //아까 처럼 id 일치하는거 찾음
        Member member = members.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        //지움
        members.remove(member);

        //존재하지 않는다 ( 성공적으로 삭제 되었다 . ) 결과 HTTP 204 no Content 반환
        return ResponseEntity.noContent().build();
    }
}
