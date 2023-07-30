/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

var main = {
    init : function(){
        var _this = this;

        $('#buy-now').on('click', function(){
            if(confirm('상품을 구매하시겠습니까?'))
                _this.order();
        });

        $('#add-cart').on('click', function(){
            if(confirm('장바구니에 추가하시겠습니까?'))
                _this.addCart();
        });

        $('#delete-cart').click(function() {
            if($("input[type=checkbox][name=listGroupRadioGrid]:checked").length == 0)
                alert('삭제할 상품을 선택해주세요.');
            else
                _this.deleteCart();
        });

        $('#order-cart').click(function() {
            if($("input[type=checkbox][name=listGroupRadioGrid]:checked").length == 0)
                alert('주문할 상품을 선택해주세요.');
            else
                _this.orderCart();
        });

        $('#checkAll').click(function(){
            if($('#checkAll').is(":checked"))
                $("input[type=checkbox][name=listGroupRadioGrid]").prop("checked", true);
            else
                $("input[type=checkbox][name=listGroupRadioGrid]").prop("checked", false);
        });

        $("input[type=checkbox][name=listGroupRadioGrid]").click(function(){
            var all = $("input[name=listGroupRadioGrid]").length;
            var checked = $("input[name=listGroupRadioGrid]:checked").length;

            if(all == checked)
                $("#checkAll").prop("checked", true);
            else
                $("#checkAll").prop("checked", false);
        });

        $('#chargeMoney').on('click', function(){
            var chargeMoney = prompt("충전할 금액을 입력해 주세요.")
            if(chargeMoney != "" && chargeMoney != null)
                _this.charge(chargeMoney);
        });

        $('button[name=cancel-orders]').on('click', function(){
            if(confirm('주문을 취소하시겠습니까?'))
                _this.cancel_order($(this).attr("id"));
        });
    },

    order : function(){
        var data = {
            member_id: $('#member_id').val(),
            goods_id: $('#goods_id').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/orders',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(result){
            if(result == -1){
                if(confirm('상품을 주문하였습니다.\n주문 내역을 확인하시겠습니까?'))
                    window.location.href='/myPage/member_id=' + $('#member_id').val();
            }
            else{
                if(confirm('보유머니가 ' + result +'원 부족합니다.\n충전하시겠습니까?'))
                    window.location.href='/myPage/member_id=' + $('#member_id').val();
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    addCart : function(){
        var data = {
            member_id: $('#member_id').val(),
            goods_id: $('#goods_id').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/cart',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(result){
            if(result != -1){
                alert('상품을 장바구니에 추가하였습니다.')
                window.location.href='/goods/goods_id=' + $('#goods_id').val();
            }
            else{
                alert('이미 장바구니에 추가되어 있습니다.')
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    deleteCart : function(){

        let delete_cart = [];

        $("input[type=checkbox][name=listGroupRadioGrid]:checked").each(function(){
            delete_cart.push($(this).val()); // push: 배열에 값 삽입
        });

        $.ajax({
         type: 'DELETE',
         url: '/api/cart',
         dataType: 'json',
         contentType:'application/json; charset=utf-8',
         data: JSON.stringify(delete_cart)
        }).done(function(result){
         alert('상품을 장바구니에서 삭제하였습니다.')
         window.location.href='/cart/member_id=' + $('#member_id').val();
        }).fail(function(error){
         alert(JSON.stringify(error));
        });
    },
    orderCart : function(){

        let order_cart = [];

        $("input[type=checkbox][name=listGroupRadioGrid]:checked").each(function(){
            order_cart.push($(this).val());
        });

        $.ajax({
         type: 'DELETE',
         url: '/api/cart/' + $('#member_id').val(),
         dataType: 'json',
         contentType:'application/json; charset=utf-8',
         data: JSON.stringify(order_cart)
        }).done(function(result){
         if(result == -1){
             if(confirm('상품을 주문하였습니다.\n주문 내역을 확인하시겠습니까?'))
                window.location.href='/myPage/member_id=' + $('#member_id').val();
             else
                window.location.href='/cart/member_id=' + $('#member_id').val();
         }
         else{
             if(confirm('보유머니가 ' + result + '원 부족합니다.\n충전하시겠습니까?'))
                window.location.href='/myPage/member_id=' + $('#member_id').val();
         }
        }).fail(function(error){
         alert(JSON.stringify(error));
        });
    },
    charge : function(chargeMoney){
        money = Number(chargeMoney);

        $.ajax({
            type: 'PUT',
            url: '/api/member/' + $('#member_id').val(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(money)
        }).done(function(){
            alert('충전하였습니다.')
            window.location.href='/myPage/member_id=' + $('#member_id').val();
        }).fail(function(error){
            alert('올바른 형식을 입력해 주세요.');
        });
    },
    cancel_order : function(order_id){

        $.ajax({
            type: 'PUT',
            url: '/api/orders/' + order_id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify()
        }).done(function(result){
            alert("주문을 취소하였습니다.");
            window.location.href='/myPage/member_id=' + $('#member_id').val();
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
};

main.init();