<template>
  <div class="mt-2 dialog-item">
    <v-row>
      <v-col cols="2">
        <v-avatar
          class="ml-3"
          color="orange lighten-4"
          size="40"
          @click="goFriendFeed"
        >
          <img :src="commentListItem.profileImageUrl" alt="image" />
        </v-avatar>
      </v-col>
      <v-col cols="8">
        <h5>
          {{ commentListItem.nickname }}
          &nbsp;&nbsp;&nbsp;&nbsp;{{ commentListItem.content }}
        </h5>
        <h5>
          {{ createDate }}
          &nbsp;&nbsp;&nbsp;좋아요 {{ commentListItem.countCommentLikes }}개
          <v-btn
            class="delete-btn"
            depressed
            small
            text
            v-if="commentListItem.userId == userId"
            @click="btnDeleteComment"
            >삭제</v-btn
          >
        </h5>
      </v-col>
      <v-col v-if="isSignin" cols="2">
        <v-btn
          class="mt-2"
          text
          icon
          color="red"
          v-if="!isLiked"
          @click="btnLikeComment"
        >
          <v-icon small>mdi-heart-outline</v-icon>
        </v-btn>
        <v-btn
          class="mt-2"
          text
          icon
          color="red"
          v-else
          @click="btnLikeComment"
        >
          <v-icon small>mdi-heart</v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <v-divider class="mt-1"></v-divider>
  </div>
</template>
<script>
import dayjs from "dayjs";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "PopsCommentItem",
  component: {
    dayjs,
  },
  props: {
    commentListItem: Object,
    popsId: {
      type: Number,
    },
  },
  data() {
    return {
      createDate: dayjs(this.commentListItem.createDate).format(
        "YYYY-MM-DD HH:mm"
      ),
      isLiked: false,
    };
  },
  created() {
    if (this.commentListItem.liked) {
      this.isLiked = true;
    }
  },
  computed: {
    ...mapGetters(["isSignin", "userId"]),
  },
  methods: {
    ...mapActions(["deleteComment", "likeComment", "unLikeComment"]),
    btnLikeComment() {
      if (!this.isLiked) {
        this.likeComment({
          popId: this.popsId,
          commentId: this.commentListItem.id,
          userId: this.userId,
        });
        this.isLiked = true;
      } else {
        this.unLikeComment({
          popId: this.popsId,
          commentId: this.commentListItem.id,
          userId: this.userId,
        });
        this.isLiked = false;
      }
    },
    btnDeleteComment() {
      if (confirm("삭제하시겠습니까?")) {
        this.deleteComment({
          commentId: this.commentListItem.id,
          userId: this.commentListItem.userId,
          popId: this.popsId,
        });
      }
    },
    goFriendFeed() {
      if (this.isSignin) {
        this.$router.push({
          name: "feed",
          params: this.commentListItem.userId,
        });
      }
    },
  },
};
</script>
<style>
.dialog-item {
  margin: 3px;
}
.delete-btn {
  color: red !important;
}
/* 스크롤바 제거 */
.no-scroll::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style>
