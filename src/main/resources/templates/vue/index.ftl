

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>介绍 — Vue.js</title>
<meta charset="utf-8">
<meta name="description" content="vue demo">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<meta name="twitter:card" content="summary">
<meta name="twitter:title" content="vue demo">
<meta name="twitter:description" content="vue demo">
<meta name="twitter:image"
	content="https://cn.vuejs.org/images/logo.png">

<meta name="msapplication-TileColor" content="#4fc08d">
<meta name="theme-color" content="#4fc08d">

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
</head>
<body>
	<div id="mobile-bar">
		<a class="menu-button"></a> <a class="logo" href="/"></a>
	</div>
	<div id="header">
		<a id="logo" href="/"> <img src="/images/logo.png"> <span>Vue.js</span>
		</a>


		<ul id="nav">
			<li>
				<form id="search-form">
					<input type="text" id="search-query-nav"
						class="search-query st-default-search-input">
				</form>
			</li>
			<li class="nav-dropdown-container learn"><a
				class="nav-link current">学习</a><span class="arrow"></span>
				<ul class="nav-dropdown">
					<li><ul>
							<li><a href="/v2/guide/" class="nav-link current">教程</a></li>
							<li><a href="/v2/api/" class="nav-link">API</a></li>
							<li><a href="/v2/style-guide/" class="nav-link">风格指南</a></li>
							<li><a href="/v2/examples/" class="nav-link">示例</a></li>
							<li><a href="/v2/cookbook/" class="nav-link">Cookbook</a></li>
						</ul></li>
				</ul></li>

			<li class="nav-dropdown-container ecosystem"><a class="nav-link">生态系统</a><span
				class="arrow"></span>
				<ul class="nav-dropdown">
					<li><h4>帮助</h4></li>
					<li><ul>
							<li><a href="https://forum.vuejs.org/" class="nav-link"
								target="_blank">论坛</a></li>
							<li><a href="https://chat.vuejs.org/" class="nav-link"
								target="_blank">聊天室</a></li>
						</ul></li>
					<li><h4>工具</h4></li>
					<li>
						<ul>
							<li><a href="https://github.com/vuejs/vue-devtools"
								class="nav-link" target="_blank">Devtools</a></li>
							<li><a href="https://vuejs-templates.github.io/webpack"
								class="nav-link" target="_blank">webpack 模板</a></li>
							<li><a href="https://vue-loader.vuejs.org" class="nav-link"
								target="_blank">Vue Loader</a></li>
						</ul>
					</li>
					<li><h4>核心插件</h4></li>
					<li><ul>
							<li><a href="https://router.vuejs.org/" class="nav-link"
								target="_blank">Vue Router</a></li>
							<li><a href="https://vuex.vuejs.org/" class="nav-link"
								target="_blank">Vuex</a></li>
							<li><a href="https://ssr.vuejs.org/" class="nav-link"
								target="_blank">Vue 服务端渲染</a></li>
						</ul></li>
					<li><h4>信息</h4></li>
					<li><ul>
							<li><a href="https://news.vuejs.org" class="nav-link"
								target="_blank">周刊</a></li>
							<li><a href="https://github.com/vuejs/roadmap"
								class="nav-link" target="_blank">Roadmap</a></li>
							<li><a href="https://twitter.com/vuejs" class="nav-link"
								target="_blank">Twitter</a></li>
							<li><a href="https://medium.com/the-vue-point"
								class="nav-link" target="_blank">博客</a></li>
							<li><a href="https://vuejobs.com/?ref=vuejs"
								class="nav-link" target="_blank">工作</a></li>
						</ul></li>
					<li><h4>资源列表</h4></li>
					<li><ul>
							<li><a href="https://github.com/vuejs" class="nav-link"
								target="_blank">官方仓库</a></li>
							<li><a href="https://curated.vuejs.org/" class="nav-link"
								target="_blank">Vue 优选</a></li>
							<li><a href="https://github.com/vuejs/awesome-vue"
								class="nav-link" target="_blank">Vue 资源</a></li>
						</ul></li>
				</ul></li>

			<li><a href="/v2/guide/team.html" class="nav-link team">团队</a></li>
			<li class="nav-dropdown-container support-vue"><a
				href="/support-vuejs/" class="nav-link">支持 Vue</a><span
				class="arrow"></span>
				<ul class="nav-dropdown">
					<li><ul>
							<li><a href="/support-vuejs/#One-time-Donations"
								class="nav-link">一次性赞助</a></li>
							<li><a href="/support-vuejs/#Recurring-Pledges"
								class="nav-link">周期性赞助</a></li>
							<li><a href="https://vue.threadless.com" target="_blank"
								class="nav-link">T 恤商店</a></li>
						</ul></li>
				</ul></li>

			<li class="nav-dropdown-container language"><a class="nav-link">多语言</a><span
				class="arrow"></span>
				<ul class="nav-dropdown">
					<li><a href="https://vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">English</a></li>
					<li><a href="https://jp.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">日本語</a></li>
					<li><a href="https://ru.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">Русский</a></li>
					<li><a href="https://kr.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">한국어</a></li>
					<li><a href="https://br.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">Português</a></li>
					<li><a href="https://fr.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">Français</a></li>
					<li><a href="https://vi.vuejs.org/v2/guide/index.html"
						class="nav-link" target="_blank">Tiếng Việt</a></li>
				</ul></li>

			<li><a href="https://github.com/vuejs/cn.vuejs.org/"
				target="_blank" class="nav-link contribute">参与翻译</a></li>

		</ul>
	</div>
</body>
</html>