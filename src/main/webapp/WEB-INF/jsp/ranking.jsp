<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="votenolivro"%>
<votenolivro:layout>
			<section>
				<p>Confira o Ranking dos Livros!</p>
			
				<c:if  test="${not empty pessoa}">
					<p>${pessoa.nome} veja seu Ranking de Acordo com Seu Voto!!</p>
					<c:forEach items="${pessoa.livros}" var="livro" varStatus="status">
						<ul>
							<li>
								${livro.nome} - ${livro.totalVoto} Voto(s)
							</li>
						</ul>
					</c:forEach>
				</c:if>
				<c:if  test="${not empty livros}">
					<p>Veja o Ranking de acordo com todos os Votos!!</p>
					<c:forEach items="${livros}" var="livro" varStatus="status">
						<ul>
							<li>
								${livro.nome} - ${livro.totalVoto} Voto(s)
							</li>
						</ul>
					</c:forEach>
				</c:if>
				<p><a href="<c:url value="/livro/"/>">VOLTAR!</a></p>
			</section>
</votenolivro:layout>