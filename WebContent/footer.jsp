<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link href="assets/css/footer/footer.css" rel="stylesheet" type="text/css">
<!-- Footer -->
<footer id="footer">
	<section>
		<form method="post" action="mailto:djagpwls10@naver.com">
			<div class="fields">
				<div class="field">
					<label for="name">Name</label>
					<input type="text" name="name" id="name">
				</div>
				<div class="field">
					<label for="email">Email</label>
					<input type="text" name="email" id="email">
				</div>
				<div class="field">
					<label for="message">Message</label>
					<textarea name="message" id="message" rows="3"></textarea>
				</div>
			</div>
			<ul class="actions">
				<li><input type="submit" value="Send Message"></li>
			</ul>
		</form>
	</section>
	<section class="split contact">
		<section>
			<h3>Phone</h3>
			<p><a href="#">010-3321-6930</a></p>
		</section>  <%-- Phone end --%>
		<section>
			<h3>Email</h3>
			<p><a href="#">djagpwls10@naver.com</a></p>
		</section> <%-- Email end --%>
		<section>
			<h3>Social</h3>
			<ul class="icons alt">
				<li><a href = "mailto:djagpwls10@naver.com"><i class="icon brands alt fa fa-envelope-o"><span class="label">Naver</span></i></a></li>
				<li><a href = "mailto:djagpwls20@gmail.com"><i class="icon brands alt fa fa-google"><span class="label">Google</span></i></a></li>
				<li><a href = "https://github.com/hyejin96"><i class="icon brands alt fa fa-github"><span class="label">Github</span></i> </a></li>
			</ul>
		</section> <%-- social end --%>
	</section>
</footer>
<div id="copyright">
		<ul><li>© Hyejin</li><li>Design: <a href="#">Hyejin</a></li></ul>
</div>