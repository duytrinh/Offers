//
//  StampGestureView.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/06/23.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <StampKit/StampKit.h>
#import <StampKit/StampView.h>
#import <StampKit/Gesture.h>


@protocol StampGestureViewDelegate <NSObject>

@optional
-(void) gestureSuccess:(NSDictionary*)result;

@optional
-(void) matchStamp:(NSDictionary*)result;


@optional
-(void) releaseStamp:(NSArray*)result;

@optional
-(void) beforeStamp:(NSDictionary*)result;

@optional
-(void) afterStamp:(NSDictionary*)result;



@optional
-(void) stampAction:(NSDictionary*)result;


@end

@interface StampGestureView : StampView<GestureDelegate, StampViewDelegate>

@property (nonatomic, assign) id delegate;

@end
