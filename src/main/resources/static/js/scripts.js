$(document).ready(function() {
	

	$("#contact").submit(function() {
		var elem = $(this);
		var urlTarget = $(this).attr("action");
		$.ajax({
			type : "POST",
			url : urlTarget,
			dataType : "html",
			data : $(this).serialize(),
			beforeSend : function() {
				elem.prepend("<div class='alert alert-info'>" + "<a class='close' data-dismiss='alert'>×</a>" + "Loading" + "</div>");
				//elem.find(".loading").show();
			},
			success : function(response) {
				elem.prepend(response);
				elem.find(".response").html(response);
				elem.find(".alert-info").hide();
				//elem.find(".alert-danger").hide();
				elem.find("input[type='text'],input[type='email'],input[type='text'],textarea").val("");
			}
		});
		return false;
	});

	MyStuffs = {


		getBreakpoint: function($this) {
			if(Modernizr.mq('only all and (max-width: 768px)')){

			}

		},

		primaryDropDown: function() {
			var $primaryLink = $("nav ul > li > ul");

			$($primaryLink).each(function() {
				if( $(this).length > 0 ) {
					$(this).parent().children("a").attr("data-handler","submenu");
				}
			});

			$("nav a[data-handler]").on("click",function(e){
				e.preventDefault();
				$(this).next("ul").toggleClass("visible-desktop");
				$(this).toggleClass("open");
			});
			
			$(".responsive-menu").on("click", function(e){
				e.preventDefault();
				$(this).next().toggleClass("visible-desktop");
				$(this).toggleClass("open");
			});

			$('#myTab a').click(function (e) {
				e.preventDefault();
				$(this).tab('show');
			});

			var $pricingTable = $(".pricing-table").length;
			if ( $pricingTable > 0 ) {
				$(".pricing-table").first().css("margin-top","-30px");
			}

		},

		heroArea: function() {
			var $wrapper = $(".wrapper").first();
			$($wrapper).prepend("<div class='bottom-hero'>&nbsp;</div>");
		},

		collapseState: function() {

			$("a[data-toggle='collapse']").on("click",function(){
				$("a[data-toggle='collapse']").removeClass("open");
				$(this).addClass("open");
			});
		},

		transitionsBootstrap: function() {

			!function ($) {

"use strict"; // jshint ;_;




/* CSS TRANSITION SUPPORT (http://www.modernizr.com/)
* ======================================================= */

$(function () {

	$.support.transition = (function () {

		var transitionEnd = (function () {

			var el = document.createElement('bootstrap')
			, transEndEventNames = {
				'WebkitTransition' : 'webkitTransitionEnd'
				,  'MozTransition'    : 'transitionend'
				,  'OTransition'      : 'oTransitionEnd otransitionend'
				,  'transition'       : 'transitionend'
			}
			, name

			for (name in transEndEventNames){
				if (el.style[name] !== undefined) {
					return transEndEventNames[name]
				}
			}

		}())

		return transitionEnd && {
			end: transitionEnd
		}

	})()

})





}(window.jQuery);

		},

		scrollTop: function() {
			$(window).scroll(function(){
				if ($(this).scrollTop() > 1450) {
					$("a.go-top").fadeIn();
				}else {
					$("a.go-top").fadeOut();
				}
			});

			$('a.go-top').on("click",function(e){
				e.preventDefault();
				$("html, body").animate({ scrollTop: 0 }, 700);
			});

		},

		sliderOnload: function() {
			var $sliderHeight = $("ul.bxslider2 li").height();
			$("ul.bxslider2").css("height",$sliderHeight);

		},

		eventsResize: function() {
			$(window).resize(function() {
				MyStuffs.sliderOnload();
			});
		},

		carouselHero: function() {
			$(".bxslider2 li").first().addClass("active").show();

			$("#slider-next, #slider-prev").on("click",function(e){
				$(".bxslider2 li.active").each(function() {
					$(this).siblings().not(".active").addClass("active").fadeIn(870);
					$(this).removeClass("active").hide();
				});
			});
		},
		

		
		

		postPaginator: function() {
			$(".pagination ul > li > a").on("click",function(e){
				e.preventDefault();
				var $num = $(this).attr("href");
				var $dataWrapper = $("[data-page-number=" + $num + "]");

				$("[data-page-number]").removeClass("active");
				$($dataWrapper).addClass("active");
				$(".pagination ul > li > a").removeClass("active");
				$(this).addClass("active");
			});
		}

	};

}());

MyStuffs.carouselHero();
MyStuffs.getBreakpoint();
MyStuffs.primaryDropDown();
MyStuffs.eventsResize();
MyStuffs.heroArea();
MyStuffs.collapseState();
MyStuffs.transitionsBootstrap();
MyStuffs.scrollTop();
MyStuffs.sliderOnload();
MyStuffs.submitForm();
MyStuffs.postPaginator();


