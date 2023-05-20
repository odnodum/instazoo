package ru.proxyva.instazoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.proxyva.instazoo.entity.Comment;
import ru.proxyva.instazoo.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long commentId, Long userId);

}
