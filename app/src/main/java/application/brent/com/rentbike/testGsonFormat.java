package application.brent.com.rentbike;

/**
 * Created by brent on 2015/6/18.
 */
public class testGsonFormat {

	/**
	 * errno : 1
	 * data : {"cancel":{"comment":null},"uid":"10226","orderid":"100165","pay":{"money":"0.00","totalMoney":"5.00","refundMoney":"0.00"},"time":{"formatAcceptTime":"-0001/11/30","deliverTimeout":14400,"formatTakeTime":"-0001/11/30","deliverElapse":0,"takeElapse":0,"formatDeliverTime":"-0001/11/30","acceptElapse":1,"mtime":"2015-06-15 12:45:54","formatMtime":"今天 12:45","takeTimeout":3600,"ctime":"2015-06-15 12:45:54","acceptTimeout":600,"formatCtime":"今天 12:45"},"category":0,"basic":{"carrierImgUrl":"","receiver":{"address":"陕西西安市雁塔区大寨路2号","phone":"12345678908","name":"二大"},"sender":{"address":"陕西西安市碑林区友谊东路30号","phone":"12345678908","name":"大大"},"customerImgUrl":"","price":"5.00","goods":{"name":"花","weight":"1.00"},"location":{"lng":"108.988487","lat":"34.247189"},"comment":"","goodsCode":""},"status":"100"}
	 * errmsg : Success
	 */
	private int errno;
	private DataEntity data;
	private String errmsg;

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public void setData(DataEntity data) {
		this.data = data;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrno() {
		return errno;
	}

	public DataEntity getData() {
		return data;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public class DataEntity {
		/**
		 * cancel : {"comment":null}
		 * uid : 10226
		 * orderid : 100165
		 * pay : {"money":"0.00","totalMoney":"5.00","refundMoney":"0.00"}
		 * time : {"formatAcceptTime":"-0001/11/30","deliverTimeout":14400,"formatTakeTime":"-0001/11/30","deliverElapse":0,"takeElapse":0,"formatDeliverTime":"-0001/11/30","acceptElapse":1,"mtime":"2015-06-15 12:45:54","formatMtime":"今天 12:45","takeTimeout":3600,"ctime":"2015-06-15 12:45:54","acceptTimeout":600,"formatCtime":"今天 12:45"}
		 * category : 0
		 * basic : {"carrierImgUrl":"","receiver":{"address":"陕西西安市雁塔区大寨路2号","phone":"12345678908","name":"二大"},"sender":{"address":"陕西西安市碑林区友谊东路30号","phone":"12345678908","name":"大大"},"customerImgUrl":"","price":"5.00","goods":{"name":"花","weight":"1.00"},"location":{"lng":"108.988487","lat":"34.247189"},"comment":"","goodsCode":""}
		 * status : 100
		 */
		private CancelEntity cancel;
		private String uid;
		private String orderid;
		private PayEntity pay;
		private TimeEntity time;
		private int category;
		private BasicEntity basic;
		private String status;

		public void setCancel(CancelEntity cancel) {
			this.cancel = cancel;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public void setPay(PayEntity pay) {
			this.pay = pay;
		}

		public void setTime(TimeEntity time) {
			this.time = time;
		}

		public void setCategory(int category) {
			this.category = category;
		}

		public void setBasic(BasicEntity basic) {
			this.basic = basic;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public CancelEntity getCancel() {
			return cancel;
		}

		public String getUid() {
			return uid;
		}

		public String getOrderid() {
			return orderid;
		}

		public PayEntity getPay() {
			return pay;
		}

		public TimeEntity getTime() {
			return time;
		}

		public int getCategory() {
			return category;
		}

		public BasicEntity getBasic() {
			return basic;
		}

		public String getStatus() {
			return status;
		}

		public class CancelEntity {
			/**
			 * comment : null
			 */
			private String comment;

			public void setComment(String comment) {
				this.comment = comment;
			}

			public String getComment() {
				return comment;
			}
		}

		public class PayEntity {
			/**
			 * money : 0.00
			 * totalMoney : 5.00
			 * refundMoney : 0.00
			 */
			private String money;
			private String totalMoney;
			private String refundMoney;

			public void setMoney(String money) {
				this.money = money;
			}

			public void setTotalMoney(String totalMoney) {
				this.totalMoney = totalMoney;
			}

			public void setRefundMoney(String refundMoney) {
				this.refundMoney = refundMoney;
			}

			public String getMoney() {
				return money;
			}

			public String getTotalMoney() {
				return totalMoney;
			}

			public String getRefundMoney() {
				return refundMoney;
			}
		}

		public class TimeEntity {
			/**
			 * formatAcceptTime : -0001/11/30
			 * deliverTimeout : 14400
			 * formatTakeTime : -0001/11/30
			 * deliverElapse : 0
			 * takeElapse : 0
			 * formatDeliverTime : -0001/11/30
			 * acceptElapse : 1
			 * mtime : 2015-06-15 12:45:54
			 * formatMtime : 今天 12:45
			 * takeTimeout : 3600
			 * ctime : 2015-06-15 12:45:54
			 * acceptTimeout : 600
			 * formatCtime : 今天 12:45
			 */
			private String formatAcceptTime;
			private int deliverTimeout;
			private String formatTakeTime;
			private int deliverElapse;
			private int takeElapse;
			private String formatDeliverTime;
			private int acceptElapse;
			private String mtime;
			private String formatMtime;
			private int takeTimeout;
			private String ctime;
			private int acceptTimeout;
			private String formatCtime;

			public void setFormatAcceptTime(String formatAcceptTime) {
				this.formatAcceptTime = formatAcceptTime;
			}

			public void setDeliverTimeout(int deliverTimeout) {
				this.deliverTimeout = deliverTimeout;
			}

			public void setFormatTakeTime(String formatTakeTime) {
				this.formatTakeTime = formatTakeTime;
			}

			public void setDeliverElapse(int deliverElapse) {
				this.deliverElapse = deliverElapse;
			}

			public void setTakeElapse(int takeElapse) {
				this.takeElapse = takeElapse;
			}

			public void setFormatDeliverTime(String formatDeliverTime) {
				this.formatDeliverTime = formatDeliverTime;
			}

			public void setAcceptElapse(int acceptElapse) {
				this.acceptElapse = acceptElapse;
			}

			public void setMtime(String mtime) {
				this.mtime = mtime;
			}

			public void setFormatMtime(String formatMtime) {
				this.formatMtime = formatMtime;
			}

			public void setTakeTimeout(int takeTimeout) {
				this.takeTimeout = takeTimeout;
			}

			public void setCtime(String ctime) {
				this.ctime = ctime;
			}

			public void setAcceptTimeout(int acceptTimeout) {
				this.acceptTimeout = acceptTimeout;
			}

			public void setFormatCtime(String formatCtime) {
				this.formatCtime = formatCtime;
			}

			public String getFormatAcceptTime() {
				return formatAcceptTime;
			}

			public int getDeliverTimeout() {
				return deliverTimeout;
			}

			public String getFormatTakeTime() {
				return formatTakeTime;
			}

			public int getDeliverElapse() {
				return deliverElapse;
			}

			public int getTakeElapse() {
				return takeElapse;
			}

			public String getFormatDeliverTime() {
				return formatDeliverTime;
			}

			public int getAcceptElapse() {
				return acceptElapse;
			}

			public String getMtime() {
				return mtime;
			}

			public String getFormatMtime() {
				return formatMtime;
			}

			public int getTakeTimeout() {
				return takeTimeout;
			}

			public String getCtime() {
				return ctime;
			}

			public int getAcceptTimeout() {
				return acceptTimeout;
			}

			public String getFormatCtime() {
				return formatCtime;
			}
		}

		public class BasicEntity {
			/**
			 * carrierImgUrl :
			 * receiver : {"address":"陕西西安市雁塔区大寨路2号","phone":"12345678908","name":"二大"}
			 * sender : {"address":"陕西西安市碑林区友谊东路30号","phone":"12345678908","name":"大大"}
			 * customerImgUrl :
			 * price : 5.00
			 * goods : {"name":"花","weight":"1.00"}
			 * location : {"lng":"108.988487","lat":"34.247189"}
			 * comment :
			 * goodsCode :
			 */
			private String carrierImgUrl;
			private ReceiverEntity receiver;
			private SenderEntity sender;
			private String customerImgUrl;
			private String price;
			private GoodsEntity goods;
			private LocationEntity location;
			private String comment;
			private String goodsCode;

			public void setCarrierImgUrl(String carrierImgUrl) {
				this.carrierImgUrl = carrierImgUrl;
			}

			public void setReceiver(ReceiverEntity receiver) {
				this.receiver = receiver;
			}

			public void setSender(SenderEntity sender) {
				this.sender = sender;
			}

			public void setCustomerImgUrl(String customerImgUrl) {
				this.customerImgUrl = customerImgUrl;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public void setGoods(GoodsEntity goods) {
				this.goods = goods;
			}

			public void setLocation(LocationEntity location) {
				this.location = location;
			}

			public void setComment(String comment) {
				this.comment = comment;
			}

			public void setGoodsCode(String goodsCode) {
				this.goodsCode = goodsCode;
			}

			public String getCarrierImgUrl() {
				return carrierImgUrl;
			}

			public ReceiverEntity getReceiver() {
				return receiver;
			}

			public SenderEntity getSender() {
				return sender;
			}

			public String getCustomerImgUrl() {
				return customerImgUrl;
			}

			public String getPrice() {
				return price;
			}

			public GoodsEntity getGoods() {
				return goods;
			}

			public LocationEntity getLocation() {
				return location;
			}

			public String getComment() {
				return comment;
			}

			public String getGoodsCode() {
				return goodsCode;
			}

			public class ReceiverEntity {
				/**
				 * address : 陕西西安市雁塔区大寨路2号
				 * phone : 12345678908
				 * name : 二大
				 */
				private String address;
				private String phone;
				private String name;

				public void setAddress(String address) {
					this.address = address;
				}

				public void setPhone(String phone) {
					this.phone = phone;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getAddress() {
					return address;
				}

				public String getPhone() {
					return phone;
				}

				public String getName() {
					return name;
				}
			}

			public class SenderEntity {
				/**
				 * address : 陕西西安市碑林区友谊东路30号
				 * phone : 12345678908
				 * name : 大大
				 */
				private String address;
				private String phone;
				private String name;

				public void setAddress(String address) {
					this.address = address;
				}

				public void setPhone(String phone) {
					this.phone = phone;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getAddress() {
					return address;
				}

				public String getPhone() {
					return phone;
				}

				public String getName() {
					return name;
				}
			}

			public class GoodsEntity {
				/**
				 * name : 花
				 * weight : 1.00
				 */
				private String name;
				private String weight;

				public void setName(String name) {
					this.name = name;
				}

				public void setWeight(String weight) {
					this.weight = weight;
				}

				public String getName() {
					return name;
				}

				public String getWeight() {
					return weight;
				}
			}

			public class LocationEntity {
				/**
				 * lng : 108.988487
				 * lat : 34.247189
				 */
				private String lng;
				private String lat;

				public void setLng(String lng) {
					this.lng = lng;
				}

				public void setLat(String lat) {
					this.lat = lat;
				}

				public String getLng() {
					return lng;
				}

				public String getLat() {
					return lat;
				}
			}
		}
	}
}
