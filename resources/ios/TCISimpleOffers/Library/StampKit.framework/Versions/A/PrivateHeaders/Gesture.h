//
//  GestureView.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/06/23.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol GestureDelegate <NSObject>

//@optional
-(void) gestureSuccess:(NSDictionary*)result;

@end

@interface Gesture: NSObject

@property (nonatomic, assign) id delegate;

-(id)initView:(UIView *)view;

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event;

@end
