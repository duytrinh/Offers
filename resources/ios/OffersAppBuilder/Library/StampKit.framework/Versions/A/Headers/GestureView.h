//
//  GestureView.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/06/24.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <StampKit/Gesture.h>

@protocol GestureViewDelegate <NSObject>

//@optional
-(void) gestureSuccess:(NSDictionary*)result;

@end

@interface GestureView : UIView<GestureDelegate>

@property (nonatomic, assign) id delegate;

@end
