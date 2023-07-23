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
                _this.save();
        });
    },

    save : function(){
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
                if(result != -1){
                    alert('상품을 결제하였습니다.');
                    if(confirm('주문 내역 보러가기'))
                        window.location.href='/myPage/' + $('#member_id').val();
                }
                else{
                    if(confirm('보유머니가 부족합니다.\n충전하시겠습니까?'))
                        window.location.href='/myPage/' + $('#member_id').val();
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
};

main.init();